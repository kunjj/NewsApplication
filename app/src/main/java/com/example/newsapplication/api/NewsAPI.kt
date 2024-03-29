package com.example.newsapplication.api

import com.example.newsapplication.models.News
import com.example.newsapplication.utils.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("apikey") apikey: String = Constant.API_KEY,
        @Query("country") countryCode: String,
        @Query("page") page: Int
    ): Response<News>

    @GET("/v2/everything")
    suspend fun searchForNews(
        @Query("apikey") apikey: String = Constant.API_KEY,
        @Query("q") searchQuery: String,
        @Query("searchIn") searchIn: String = "title"
    ): Response<News>
}