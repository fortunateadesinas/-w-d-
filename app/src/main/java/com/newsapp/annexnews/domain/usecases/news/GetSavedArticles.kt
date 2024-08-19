package com.newsapp.annexnews.domain.usecases.news

import com.newsapp.annexnews.data.local.NewsDao
import com.newsapp.annexnews.domain.model.Article
import kotlinx.coroutines.flow.Flow

class GetSavedArticles(
    private val newsDao: NewsDao
) {

    operator fun invoke(): Flow<List<Article>>{
        return newsDao.getArticles()
    }


}