package com.example.newsapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentArticleBinding
import com.example.newsapplication.ui.NewsActivity
import com.example.newsapplication.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    private val viewModel: NewsViewModel by lazy { (activity as NewsActivity).newsViewModel }
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_article, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
            if (!article.author.isNullOrEmpty()) binding.fabBookmarkArticle.visibility =
                View.VISIBLE
        }

        binding.fabBookmarkArticle.setOnClickListener {
            viewModel.savedArticle(article)
            Snackbar.make(
                view,
                getString(R.string.article_saved_successfully),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}