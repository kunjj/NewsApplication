package com.example.newsapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapplication.R
import com.example.newsapplication.database.ArticleDatabase
import com.example.newsapplication.databinding.ActivityNewsBinding
import com.example.newsapplication.repository.NewsRepository
import com.example.newsapplication.viewmodels.NewsViewModel
import com.example.newsapplication.viewmodels.NewsViewModelProviderFactory

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    lateinit var newsViewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        val newsRepo = NewsRepository(ArticleDatabase(context = this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepo)
        this.newsViewModel =
            ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        // Setting Up Bottom Navigation View.
        binding.bottomNavigationView.setupWithNavController(binding.newsNavHostFragment.findNavController())
    }
}