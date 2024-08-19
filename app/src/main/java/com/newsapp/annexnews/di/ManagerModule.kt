package com.newsapp.annexnews.di

import com.newsapp.annexnews.data.manager.LocalUserManagerImpl
import com.newsapp.annexnews.domain.manager.LocalUserManager
import org.koin.dsl.module

val managerModule = module {
    single<LocalUserManager> { LocalUserManagerImpl(get()) }
}