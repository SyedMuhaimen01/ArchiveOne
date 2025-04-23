package com.muhaimen.archiveone.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.muhaimen.archiveone.R
import com.muhaimen.archiveone.data.dataclass.Article
import com.muhaimen.archiveone.databinding.ActivityViewArticleBinding
import com.muhaimen.archiveone.viewModel.ArticleViewModel


class ViewArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewArticleBinding
    private val article: Article? by lazy {
        intent.getParcelableExtra<Article>("article")
    }
    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_article)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityViewArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        article?.let { article ->
            binding.articleTitle.setText(article.title)
            binding.articleContent.setText(article.content)
            binding.articleAuthor.setText(article.author)
            val formattedDate = java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault())
                .format(java.util.Date(article.timeStamp))
            binding.articleTime.setText(formattedDate)
            binding.deleteButton.setOnClickListener {
                articleViewModel.delete(article)
                finish()
            }
        } ?: run {
            // Handle the case where the article is null
            finish()
        }

        binding.editButton.setOnClickListener {
            val title = binding.articleTitle.text.toString()
            val content = binding.articleContent.text.toString()
            val author = binding.articleAuthor.text.toString()
            val timeStamp = article?.timeStamp ?: System.currentTimeMillis()

            if (article != null) {
                val updatedArticle = article!!.copy(title = title, content = content, author = author, timeStamp = timeStamp)
                articleViewModel.insert(updatedArticle)
                finish()
            }
        }

        binding.deleteButton.setOnClickListener {
            article?.let { article ->
                articleViewModel.delete(article)
                finish()
            }
        }
    }

}