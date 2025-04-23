package com.muhaimen.archiveone.data.repository

import com.muhaimen.archiveone.data.dao.ArticleDao
import com.muhaimen.archiveone.data.dataclass.Article
import kotlinx.coroutines.flow.Flow

class ArticleRepository(private val articleDao: ArticleDao) {

    val allArticles: Flow<List<Article>> = articleDao.getAllArticles()

    suspend fun insert(article: Article) {
        articleDao.insertArticle(article)
    }

    suspend fun delete(article: Article) {
        articleDao.deleteArticle(article)
    }

    suspend fun countArticles(): Int {
        return articleDao.countArticles()
    }
}