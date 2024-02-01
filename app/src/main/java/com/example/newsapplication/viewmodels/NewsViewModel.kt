package com.example.newsapplication.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.NewsApplication
import com.example.newsapplication.R
import com.example.newsapplication.models.Article
import com.example.newsapplication.models.News
import com.example.newsapplication.repository.NewsRepository
import com.example.newsapplication.utils.Result
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    val topHeadlines: MutableLiveData<Result<News>> = MutableLiveData()
    var topHeadlinesResponse: News? = null
    var topHeadlinesPageNumber = 1

    val searchNews: MutableLiveData<Result<News>> = MutableLiveData()
    private var searchNewsPageNumber = 1

    init {
        getTopHeadlines("in")
        getSavedArticles()
    }

    fun getTopHeadlines(countryCode: String) = viewModelScope.launch {
        topHeadlines.postValue(Result.Loading())
        if(isConnectedToInternet()){
            val response = newsRepository.getTopHeadlines(countryCode, topHeadlinesPageNumber)
            topHeadlines.postValue(handleNewsResponse(response))
        }
        else topHeadlines.postValue(Result.Error(NewsApplication.getApplicationContext().getString(R.string.not_connected_to_internet)))
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
                topHeadlinesPageNumber++
                if (topHeadlinesResponse == null) topHeadlinesResponse = newsResponse
                else topHeadlinesResponse!!.articles.addAll(newsResponse.articles)
                return Result.Success(topHeadlinesResponse ?: newsResponse)
            }
        } else return Result.Error(response.message())
    }

    private fun handleSearchNewsQueryResponse(response: Response<News>): Result<News> {
        if (response.isSuccessful) response.body()!!
            .let { newsResponse -> return Result.Success(newsResponse) }
        else return Result.Error(response.message())
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            NewsApplication.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: return false
        }
    }
}