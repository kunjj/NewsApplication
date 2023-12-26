package com.example.newsapplication.models

data class NewsModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)