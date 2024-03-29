package com.example.newsapplication.repository

import com.example.newsapplication.api.RetroFitInstance
import com.example.newsapplication.database.ArticleDatabase
import com.example.newsapplication.models.Article

class NewsRepository(private val db: ArticleDatabase) {

    // Fetching Data From API.
    suspend fun getTopHeadlines(country: String, pageNumber: Int) =
        RetroFitInstance.api.getTopHeadlines(countryCode = country, page = pageNumber)

    suspend fun searchNews(searchQuery: String) =
        RetroFitInstance.api.searchForNews(searchQuery = searchQuery)

    // Saving, Fetching & Deleting Data From Room.
    suspend fun saveArticle(article: Article) = db.getArticleDao().saveArticle(article)

    fun getSavedArticles() = db.getArticleDao().getSavedArticles()

    suspend fun deleteSavedArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}