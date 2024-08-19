package com.newsapp.annexnews.domain.usecases.news

import androidx.paging.PagingData
import com.newsapp.annexnews.data.remote.NewsApi
import com.newsapp.annexnews.domain.model.Article
import com.newsapp.annexnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SearchNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.searchNews(
            searchQuery = searchQuery,
            sources = sources
        )
    }
}