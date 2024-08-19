package com.newsapp.annexnews.di

import com.newsapp.annexnews.data.repository.NewsRepositoryImpl
import com.newsapp.annexnews.domain.repository.NewsRepository
import com.newsapp.annexnews.domain.usecases.app_entry.ReadAppEntry
import org.koin.dsl.module

val repositoryModule = module {
    single<NewsRepository> { NewsRepositoryImpl(get(), get()) }
    factory { ReadAppEntry(get()) }
}