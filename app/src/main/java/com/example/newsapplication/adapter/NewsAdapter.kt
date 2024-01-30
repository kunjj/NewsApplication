package com.example.newsapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ItemArticlePreviewBinding
import com.example.newsapplication.models.Article
import java.text.SimpleDateFormat
import java.util.Locale

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    class NewsHolder(val binding: ItemArticlePreviewBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
    }

    val articleList = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemArticlePreviewBinding>(
            from,
            R.layout.item_article_preview,
            parent,
            false
        )
        return NewsHolder(binding)
    }

    override fun getItemCount() = articleList.currentList.size

    private var onItemClickListener: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val article = articleList.currentList[position]
        holder.binding.apply {
            clArticle.setOnClickListener { onItemClickListener?.let { it(article) } }
            Glide.with(holder.itemView).load(article.urlToImage).into(ivArticleImage)
            tvSource.text = article.source!!.name
            tvDescription.text = article.description
            tvTitle.text = article.title
            tvPublishedAt.text = article!!.publishedAt?.let { changeDateFormat(it) }
        }
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    private fun changeDateFormat(date: String): String {
        //Inorder to change the date format from yyyy-MM-dd to dd-MMM-yyyy.
        val inputDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val outputDateFormatter = SimpleDateFormat("dd-MMM-yyyy", Locale.US)

        return outputDateFormatter.format(inputDateFormatter.parse(date)!!)
    }
}