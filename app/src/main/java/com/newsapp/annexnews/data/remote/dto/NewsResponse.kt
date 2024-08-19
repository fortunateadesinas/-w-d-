package com.newsapp.annexnews.data.remote.dto

import com.newsapp.annexnews.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)