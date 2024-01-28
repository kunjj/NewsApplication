package com.example.newsapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.R
import com.example.newsapplication.adapter.NewsAdapter
import com.example.newsapplication.databinding.FragmentBreakingNewsBinding
import com.example.newsapplication.ui.NewsActivity
import com.example.newsapplication.utils.Result
import com.example.newsapplication.viewmodels.NewsViewModel

class BreakingNewsFragment : Fragment() {
    private lateinit var binding: FragmentBreakingNewsBinding
    private val viewModel: NewsViewModel by lazy { (activity as NewsActivity).newsViewModel }
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_breaking_news, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.viewModel.news.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Success -> {
                    response.data!!.let { newsResponse ->
                        newsAdapter.articleList.submitList(
                            newsResponse.articles
                        )
                    }
                }
                else -> {}
            }
        }

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
    }

    private fun setupRecyclerView() {
        this.newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }
}