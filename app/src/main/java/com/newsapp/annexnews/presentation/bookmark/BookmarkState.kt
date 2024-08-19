package com.newsapp.annexnews.presentation.bookmark

import com.newsapp.annexnews.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)