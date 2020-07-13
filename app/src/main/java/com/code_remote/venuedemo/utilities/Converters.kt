package com.code_remote.venuedemo.utilities

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStringToList(value: String?): List<String>? {
        return value?.let { value.split(",") }
    }

    @TypeConverter
    fun fromListToString(list: List<String>?): String? {
        return list?.joinToString(",")
    }
}