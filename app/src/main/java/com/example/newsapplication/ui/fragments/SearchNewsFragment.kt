package com.example.newsapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentSearchNewsBinding
import com.example.newsapplication.ui.NewsActivity
import com.example.newsapplication.viewmodels.NewsViewModel

class SearchNewsFragment : Fragment() {
    private lateinit var binding : FragmentSearchNewsBinding
    private val viewModel : NewsViewModel by lazy { (activity as NewsActivity).newsViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_news,container,false)
        return binding.root
    }
}