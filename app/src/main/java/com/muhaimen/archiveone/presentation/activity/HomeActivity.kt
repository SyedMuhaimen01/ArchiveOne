package com.muhaimen.archiveone.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
            }
        )

        binding.articlesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.articlesRecyclerView.adapter = articlesAdapter

        setupSwipeToDelete()
        observeArticles()
    }

    private fun setupSwipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = articlesAdapter.currentList[position]
                articleViewModel.delete(article)
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.articlesRecyclerView)
    }

    private fun observeArticles() {
        lifecycleScope.launch {
            articleViewModel.allArticles.collect {
                articlesAdapter.submitList(it)
            }
        }
    }
}
