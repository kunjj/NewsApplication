package com.example.newsapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.models.Article
import com.example.newsapplication.models.News
import com.example.newsapplication.repository.NewsRepository
import com.example.newsapplication.utils.Result
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    val topHeadlines: MutableLiveData<Result<News>> = MutableLiveData()
    val topHeadlinesResponse : News? = null
    private var topHeadlinesPageNumber = 1

    val searchNews: MutableLiveData<Result<News>> = MutableLiveData()
    private var searchNewsPageNumber = 1

    init {
        getTopHeadlines("in")
        getSavedArticles()
    }

    fun getTopHeadlines(countryCode: String) = viewModelScope.launch {
        topHeadlines.postValue(Result.Loading())
        val response = newsRepository.getTopHeadlines(countryCode, topHeadlinesPageNumber)
        topHeadlines.postValue(handleNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Result.Loading())
        val response = newsRepository.searchNews(searchQuery)
        searchNews.postValue(handleSearchNewsQueryResponse(response))
    }


    fun savedArticle(article: Article) =
        viewModelScope.launch { newsRepository.saveArticle(article) }

    fun getSavedArticles() = newsRepository.getSavedArticles()

    fun deleteArticle(article: Article) =
        viewModelScope.launch { newsRepository.deleteSavedArticle(article) }

    private fun handleNewsResponse(response: Response<News>): Result<News> {
        if (response.isSuccessful) {
            response.body()!!.let { newsResponse ->
                return Result.Success(newsResponse)
            }
        } else return Result.Error(response.message())
    }

    private fun handleSearchNewsQueryResponse(response: Response<News>): Result<News> {
        if (response.isSuccessful) response.body()!!
            .let { newsResponse ->
                topHeadlinesPageNumber++
                return Result.Success(newsResponse) }
        else return Result.Error(response.message())
    }
}