package com.example.mapofitness.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: String = "",
    val username: String? = null,
    val profilePictureUrl: String? = null,
    val email: String? = null,
    val age: Int? = null,
    val gender: Gender = Gender.UNKNOWN
    // Add other fields relevant to your user
){
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userId" to userId,
            "username" to username,
            "profilePictureUrl" to profilePictureUrl,
            "email" to email,
            "age" to age,
            "gender" to gender.name
        )
    }
}

object UserManager {
    var currentUser: User? = null
        private set

    fun setUser(user: User) {
        currentUser = user
    }
}

enum class Gender {
    MALE,
    FEMALE,
    UNKNOWN
}