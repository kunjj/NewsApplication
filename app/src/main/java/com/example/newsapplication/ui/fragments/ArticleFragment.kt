package com.example.newsapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentArticleBinding
import com.example.newsapplication.ui.NewsActivity
import com.example.newsapplication.viewmodels.NewsViewModel

class ArticleFragment : Fragment() {
    private lateinit var binding : FragmentArticleBinding
    private val viewmodel : NewsViewModel by lazy { (activity as NewsActivity).newsViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        this.binding = DataBindingUtil.inflate(inflater,R.layout.fragment_article,container,false)
        return binding.root
    }

}