package com.newsapp.annexnews.domain.usecases.news

import com.newsapp.annexnews.data.local.NewsDao
import com.newsapp.annexnews.domain.model.Article

class DeleteArticle (
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(article: Article){
        newsDao.delete(article = article)
    }

}