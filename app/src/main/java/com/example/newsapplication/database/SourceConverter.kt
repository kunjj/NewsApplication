package com.example.newsapplication.database

import androidx.room.TypeConverter
import com.example.newsapplication.models.Source
import com.google.gson.Gson

class SourceConverter {
    @TypeConverter
    fun fromSourceToString(source: Source): String? = Gson().toJson(source) ?: null

    @TypeConverter
    fun fromStringToSource(string: String): Source? = Gson().fromJson(string, Source::class.java)
}