package com.example.newsapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.models.News
import com.example.newsapplication.repository.NewsRepository
import com.example.newsapplication.utils.Result
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    val news: MutableLiveData<Result<News>> = MutableLiveData()
    val errorMsg = MutableLiveData<String>()
    private var newsPageNumber = 1

    val searchNews: MutableLiveData<Result<News>> = MutableLiveData()
    private var searchNewsPageNumber = 1

    init {
        getNews("in")
    }

    private fun getNews(countryCode: String) =
        viewModelScope.launch {
            news.postValue(Result.Loading())
            val response = newsRepository.getNews(countryCode, newsPageNumber)
            news.postValue(handleNewsResponse(response))
        }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Result.Loading())
        val response = newsRepository.searchNews(searchQuery)
        searchNews.postValue(handleSearchNewsQueryResponse(response))
    }

    private fun handleNewsResponse(response: Response<News>): Result<News> {
        if (response.isSuccessful) {
            response.body()!!.let { newsResponse -> return Result.Success(newsResponse) }
        } else return Result.Error(response.message())
    }

    private fun handleSearchNewsQueryResponse(response: Response<News>): Result<News> {
        if (response.isSuccessful) response.body()!!
            .let { newsResponse -> return Result.Success(newsResponse) }
        else return Result.Error(response.message())
    }
}