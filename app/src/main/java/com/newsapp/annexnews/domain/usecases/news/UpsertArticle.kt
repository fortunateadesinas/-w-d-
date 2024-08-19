package com.newsapp.annexnews.domain.usecases.news

import com.newsapp.annexnews.data.local.NewsDao
import com.newsapp.annexnews.domain.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpsertArticle(
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(article: Article){
        withContext(Dispatchers.IO) {
            newsDao.upsert(article)
        }
    }

}