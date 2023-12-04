package com.example.mapofitness.data.local.database

import com.example.mapofitness.data.local.dao.UserDao
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mapofitness.data.local.entity.Converters
import com.example.mapofitness.data.local.entity.User

@Database(entities = [User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    // Define other DAOs if necessary
}