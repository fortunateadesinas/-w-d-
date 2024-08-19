package com.newsapp.annexnews.domain.usecases.news

import androidx.paging.PagingData
import com.newsapp.annexnews.domain.model.Article
import com.newsapp.annexnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getNews(sources = sources)
    }
}