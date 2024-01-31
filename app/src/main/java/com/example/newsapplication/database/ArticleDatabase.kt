package com.example.newsapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapplication.models.Article


@Database(entities = [Article::class], version = 2, exportSchema = false)
@TypeConverters(SourceConverter::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: ArticleDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            getInstance(context).also { LOCK = it }
        }

        private fun getInstance(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ArticleDatabase::class.java,
            "ArticleDatabase"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}