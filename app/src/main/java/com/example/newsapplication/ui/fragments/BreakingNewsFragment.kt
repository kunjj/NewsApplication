package com.example.newsapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.adapter.NewsAdapter
import com.example.newsapplication.databinding.FragmentBreakingNewsBinding
import com.example.newsapplication.ui.NewsActivity
import com.example.newsapplication.utils.Constant
import com.example.newsapplication.utils.Result
import com.example.newsapplication.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlin.math.min

class BreakingNewsFragment : Fragment() {
    private lateinit var binding: FragmentBreakingNewsBinding
    private val viewModel: NewsViewModel by lazy { (activity as NewsActivity).newsViewModel }
    private lateinit var newsAdapter: NewsAdapter
    var isAtLastNewsPage = false
    var isScrolling = false

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

        this.viewModel.topHeadlines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Success -> {
                    response.data!!.let { newsResponse ->
                        newsAdapter.articleList.submitList(
                            newsResponse.articles.toList()
                        )
                        val totalNewsPages = newsResponse.totalResults / Constant.PAGE_SIZE + 2
                        isAtLastNewsPage = totalNewsPages == viewModel.topHeadlinesPageNumber
                    }
                }

                is Result.Error -> {
                    when (response.message) {
                        requireActivity().getString(R.string.not_connected_to_internet) -> Snackbar.make(
                            view,
                            requireActivity().getString(R.string.not_connected_to_internet),
                            Snackbar.LENGTH_SHORT
                        ).show()
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

        binding.swrlNews.setOnRefreshListener {
            viewModel.getTopHeadlines("in")
            binding.swrlNews.isRefreshing = false
        }
    }

    private var scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) isScrolling =
                true
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalNews = layoutManager.itemCount
            val visibleNews = layoutManager.childCount
            val firstVisibleNewsPosition = layoutManager.findFirstVisibleItemPosition()

            var isAtLastNewsOfPage =
                min((firstVisibleNewsPosition + visibleNews), totalNews) == totalNews
            var isNotAtBeginning = firstVisibleNewsPosition >= 0
            var isTotalNewsMoreThanVisible = totalNews >= Constant.PAGE_SIZE

            val shouldPaginate =
                isScrolling && !isAtLastNewsPage && isAtLastNewsOfPage && isNotAtBeginning && isTotalNewsMoreThanVisible

            if (shouldPaginate) {
                viewModel.getTopHeadlines("in")
                isScrolling = false
            }
        }
    }

    private fun setupRecyclerView() {
        this.newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }
}