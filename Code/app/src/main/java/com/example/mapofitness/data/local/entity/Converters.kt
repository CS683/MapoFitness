package com.example.mapofitness.data.local.entity

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromGender(value: Gender): String {
        return value.name
    }

    @TypeConverter
    fun toGender(value: String): Gender {
        return Gender.valueOf(value)
    }
}