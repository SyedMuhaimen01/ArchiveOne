package com.muhaimen.archiveone.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhaimen.archiveone.data.dataclass.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT * FROM articles ORDER BY timeStamp DESC")
    fun getAllArticles(): Flow<List<Article>>

    @Query("SELECT COUNT(*) FROM articles")
    suspend fun countArticles(): Int
}