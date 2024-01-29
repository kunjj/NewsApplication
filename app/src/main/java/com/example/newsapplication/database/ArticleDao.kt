package com.example.newsapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapplication.models.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article: Article)

    @Query("SELECT * FROM Article")
    fun getSavedArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}