package com.example.newsapplication.utils

sealed class Result<News>(val data: News? = null, val message: String? = null) {
    class Success<News>(data : News) : Result<News>(data)

    class Error<News>(message: String) : Result<News>(message = message)

    class Loading<News> : Result<News>()
}