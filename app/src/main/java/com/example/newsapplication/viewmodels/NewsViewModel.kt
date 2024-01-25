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
    private var pageNumber = 1

    fun getNews(countryCode: String) =
        viewModelScope.launch {
            news.postValue(Result.Loading())
            val response = newsRepository.getNews(countryCode, pageNumber)
            news.postValue(handleNewsResponse(response))
        }

    private fun handleNewsResponse(response: Response<News>): Result<News> {
        if (response.isSuccessful) {
            response.body()!!.let { newsResponse -> return Result.Success(newsResponse) }
        } else return Result.Error(response.message())
    }
}