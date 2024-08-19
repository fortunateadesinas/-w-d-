package com.newsapp.annexnews.domain.usecases.news

import com.newsapp.annexnews.data.local.NewsDao
import com.newsapp.annexnews.domain.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetSavedArticle (
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(url: String): Article?{
        return withContext(Dispatchers.IO) { // Use Dispatchers.IO for database operations
            newsDao.getArticle(url)
        }
    }

}