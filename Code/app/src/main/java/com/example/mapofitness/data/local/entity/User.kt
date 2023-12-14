package com.example.mapofitness.data.local.entity

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: String = "",
    val username: String? = null,
    val profilePictureUrl: String? = null,
    var email: String? = null,
    var dob: String? = null,
    var gender: Gender = Gender.UNKNOWN,
    var weight: Float? = null,
    var height: Float? = null
    // Add other fields relevant to your user
){
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userId" to userId,
            "username" to username,
            "profilePictureUrl" to profilePictureUrl,
            "email" to email,
            "dob" to dob,
            "gender" to gender.name,
            "weight" to weight,
            "height" to height
        )
    }
}

object UserManager {
    private var _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    private val auth = Firebase.auth

    private fun setUser(user: User) {
        _currentUser.value = user
    }

    fun getDOB(): String? {
        return _currentUser.value?.dob
    }

    fun setDOB(dob: String) {
        _currentUser.value?.dob = dob
    }

    fun getGender(): Gender? {
        return _currentUser.value?.gender
    }

    fun setGender(gender: Gender) {
        _currentUser.value?.gender = gender
    }

    fun getHeight(): Float? {
        return _currentUser.value?.height
    }

    fun setHeight(height: Float) {
        _currentUser.value?.height = height
    }
    fun getWeight(): Float? {
        return _currentUser.value?.weight
    }

    fun setWeight(weight: Float){
        _currentUser.value?.weight = weight
    }

    fun setUserData() {
        val userId = _currentUser.value!!.userId
        val user = _currentUser.value!!
        val userRef = FirebaseDatabase.getInstance().getReference("users")
        userRef.child(userId).get().addOnSuccessListener {
            val userMap = user.toMap()
            userRef.child(userId).updateChildren(userMap)
                .addOnSuccessListener {
                    Log.i("Database", "Successful")
                }
                .addOnFailureListener {
                    Log.i("Database", "Failed")
                }
        }.addOnFailureListener {
            Log.e("Firebase", "Error fetching user data")
        }
    }

    fun fetchUserData(){
        val userId = getSignedInUser()!!.userId
        val user = getSignedInUser()
        val userRef = FirebaseDatabase.getInstance().getReference("users")
        userRef.child(userId).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val existUser = dataSnapshot.getValue(User::class.java)
                if (existUser != null) {
                    setUser(existUser)
                }
            } else {
                val userMap = user?.toMap()
                if(user != null){
                    userRef.child(user.userId).setValue(userMap)
                        .addOnSuccessListener {
                            Log.i("Database", "Successful")
                        }
                        .addOnFailureListener {
                            Log.i("Database", "Failed")
                        }
                }
                if (user != null) {
                    setUser(user)
                }
            }
            Log.i("Info", "Current user is: ${UserManager.currentUser.value?.username}")
        }.addOnFailureListener {
            Log.e("Firebase", "Error fetching user data")
        }
    }

    fun addWeightRecord(weightRecord: WeightRecord) {
        val userId = _currentUser.value?.userId
        val databaseReference = FirebaseDatabase.getInstance().getReference("users/$userId/weightRecords")
        val recordId = databaseReference.push().key // Generates a unique ID for the record

        recordId?.let {
            databaseReference.child(it).setValue(weightRecord)
                .addOnSuccessListener {
                    Log.i("DB", "Add Weight Record Successful")
                }
                .addOnFailureListener {
                    Log.i("DB", "Error Add Weight Record")
                }
        }
    }

    fun getWeightRecords(userId: String, callback: (List<WeightRecord>) -> Unit) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("users/$userId/weightRecords")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val records = snapshot.children.mapNotNull { it.getValue(WeightRecord::class.java) }
                callback(records)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    fun fetchRecentWeightRecords(callback: (List<WeightRecord>) -> Unit) {
        val userId = _currentUser.value?.userId
        val databaseReference = FirebaseDatabase.getInstance().getReference("users/$userId/weightRecords")

        // Query for the last 7 records
        databaseReference.orderByChild("date").limitToLast(7)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val records = mutableListOf<WeightRecord>()
                    snapshot.children.mapNotNullTo(records) { it.getValue(WeightRecord::class.java) }
                    // Since Firebase returns ascending, we reverse to get the latest first
                    records.sortBy { it.date } // Ensure the dates are sorted in ascending order
                    callback(records)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error fetching weight records", error.toException())
                }
            })
    }




    private fun getSignedInUser(): User? = auth.currentUser?.run {
        User(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }
}

enum class Gender {
    MALE,
    FEMALE,
    UNKNOWN
}