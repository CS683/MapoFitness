package com.example.mapofitness.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mapofitness.data.local.entity.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User): Long

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    // Add other DAO methods as needed (update, delete, etc.)
}