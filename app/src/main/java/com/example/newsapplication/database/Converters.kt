package com.example.newsapplication.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import javax.xml.transform.Source

class Converters {
    @TypeConverter
    fun fromSourceToString(source: Source): String? = Gson().toJson(source) ?: null

    @TypeConverter
    fun fromStringToSource(string: String): Source? = Gson().fromJson(string, Source::class.java)
}