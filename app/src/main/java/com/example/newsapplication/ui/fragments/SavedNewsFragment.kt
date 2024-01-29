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
import com.example.newsapplication.databinding.FragmentSavedNewsBinding
import com.example.newsapplication.ui.NewsActivity
import com.example.newsapplication.viewmodels.NewsViewModel

class SavedNewsFragment : Fragment() {
    private lateinit var binding: FragmentSavedNewsBinding
    private val viewModel: NewsViewModel by lazy { (activity as NewsActivity).newsViewModel }
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_news, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.getSavedArticles().observe(viewLifecycleOwner) {
            newsAdapter.articleList.submitList(it)
        }

        binding.swrlNews.setOnRefreshListener {
            viewModel.getSavedArticles()
            binding.swrlNews.isRefreshing = false
        }

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }
    }

    private fun setupRecyclerView() {
        this.newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            adapter = this@SavedNewsFragment.newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}