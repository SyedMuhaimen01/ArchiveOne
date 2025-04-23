package com.muhaimen.archiveone.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhaimen.archiveone.R
import com.muhaimen.archiveone.databinding.ActivityHomeBinding
import com.muhaimen.archiveone.presentation.adapter.HomeActivityArticlesAdapter
import com.muhaimen.archiveone.viewModel.ArticleViewModel
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val articleViewModel: ArticleViewModel by viewModels()
    private lateinit var articlesAdapter: HomeActivityArticlesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*  val typedValue= TypedValue()
          val theme = theme
          theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)
          window.statusBarColor = typedValue.data
          window.navigationBarColor = typedValue.data*/

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addArticleFab.setOnClickListener {
            val intent = Intent(this, AddArticleActivity::class.java)
            startActivity(intent)
        }
        articlesAdapter = HomeActivityArticlesAdapter(
            onArticleClick = { article ->
                val intent = Intent(this, ViewArticleActivity::class.java)
                intent.putExtra("article", article)
                startActivity(intent)
            },
            onArticleLongClick = { article ->
                val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Delete Article")
                    .setMessage("Are you sure you want to delete this article?")
                    .setPositiveButton("Delete") { _, _ ->
                        articleViewModel.delete(article)
                    }
                    .setNegativeButton("Cancel", null)
                    .create()
                dialog.show()
            }
        )
        binding.articlesRecyclerView.adapter = articlesAdapter
        binding.articlesRecyclerView.layoutManager=LinearLayoutManager(this)
        observeArticles()
    }

    private fun observeArticles() {
        lifecycleScope.launch {
            articleViewModel.allArticles.collect() {
                articlesAdapter.submitList(it)
            }
        }
    }
}