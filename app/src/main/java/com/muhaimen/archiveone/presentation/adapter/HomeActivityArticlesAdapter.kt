package com.muhaimen.archiveone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muhaimen.archiveone.data.dataclass.Article
import com.muhaimen.archiveone.databinding.ArticleItemBinding


class HomeActivityArticlesAdapter(
    private val onArticleClick: (Article) -> Unit,
    private val onArticleLongClick: (Article) -> Unit
) : ListAdapter<Article, HomeActivityArticlesAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    inner class ArticleViewHolder(
        private val binding: ArticleItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.articleTitle.text = article.title
            binding.articleContent.text = article.content
            binding.articleAuthor.text = article.author

            val formattedDate = java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault())
                .format(java.util.Date(article.timeStamp))
            binding.articleTime.text = formattedDate

            binding.root.setOnClickListener {
                onArticleClick(article)
            }

            binding.root.setOnLongClickListener {
                onArticleLongClick(article)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}
