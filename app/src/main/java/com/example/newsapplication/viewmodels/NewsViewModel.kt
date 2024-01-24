package com.example.newsapplication.viewmodels

import androidx.lifecycle.ViewModel
import com.example.newsapplication.repository.NewsRepository

class NewsViewModel(val newsRepository : NewsRepository) : ViewModel() {
}