package com.example.newsapplication.repository

import com.example.newsapplication.api.RetroFitInstance
import com.example.newsapplication.database.ArticleDatabase

class NewsRepository(val db: ArticleDatabase) {

    suspend fun getNews(country: String, pageNumber: Int) =
        RetroFitInstance.api.getNews(countryCode = country, page = pageNumber)

    suspend fun searchNews(searchQuery: String) =
        RetroFitInstance.api.searchForNews(searchQuery = searchQuery)
}