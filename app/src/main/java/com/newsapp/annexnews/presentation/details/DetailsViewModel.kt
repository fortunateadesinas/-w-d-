package com.newsapp.annexnews.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.annexnews.domain.model.Article
import com.newsapp.annexnews.domain.usecases.news.DeleteArticle
import com.newsapp.annexnews.domain.usecases.news.GetSavedArticle
import com.newsapp.annexnews.domain.usecases.news.UpsertArticle
import com.newsapp.annexnews.util.UIComponent
import kotlinx.coroutines.launch

class DetailsViewModel (
    private val getSavedArticleUseCase: GetSavedArticle,
    private val deleteArticleUseCase: DeleteArticle,
    private val upsertArticleUseCase: UpsertArticle
): ViewModel() {

    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = getSavedArticleUseCase(url = event.article.url)
                    if (article == null){
                        upsertArticle(article = event.article)
                    }else{
                        deleteArticle(article = event.article)
                    }
                }
            }
            is DetailsEvent.RemoveSideEffect ->{
                sideEffect = null
            }
        }
    }

    private suspend fun deleteArticle(article: Article) {
        deleteArticleUseCase(article = article)
        sideEffect = UIComponent.Toast("Article deleted")
    }

    private suspend fun upsertArticle(article: Article) {
        viewModelScope.launch { // Launch a coroutine in the viewModelScope
            upsertArticleUseCase(article)
        }
        sideEffect = UIComponent.Toast("Article Inserted")
    }
}