package com.newsapp.annexnews

import android.app.Application
import appModule
import com.newsapp.annexnews.di.managerModule
import com.newsapp.annexnews.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsApplication)
            modules(appModule, managerModule, repositoryModule)
        }
    }
}
