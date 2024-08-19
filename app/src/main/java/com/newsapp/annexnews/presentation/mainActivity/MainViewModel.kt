package com.newsapp.annexnews.presentation.mainActivity

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.newsapp.annexnews.domain.usecases.app_entry.ReadAppEntry
import com.newsapp.annexnews.presentation.navgraph.Route
import com.newsapp.annexnews.presentation.notification.NewsNotificationWorker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit

class MainViewModel (
    readAppEntry: ReadAppEntry,
    private val application: Application
): ViewModel() {

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Route.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination

    fun startNotificationWorker() {
        val notificationWorkRequest = PeriodicWorkRequestBuilder<NewsNotificationWorker>(2, TimeUnit.MINUTES // Repeat every 1 hours
        ).build()
        WorkManager.getInstance(application).enqueueUniquePeriodicWork(
            "news_notification_work",
            ExistingPeriodicWorkPolicy.REPLACE,
            notificationWorkRequest
        )
    }

    init {
        readAppEntry().onEach { shouldStartFromHomeScreen ->
            if(shouldStartFromHomeScreen){
                _startDestination.value = Route.NewsNavigation.route
            }else{
                _startDestination.value = Route.AppStartNavigation.route
            }
            delay(300) //Without this delay, the onBoarding screen will show for a momentum.
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}
