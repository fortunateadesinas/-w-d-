package com.newsapp.annexnews.presentation.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.newsapp.annexnews.R
import com.newsapp.annexnews.data.remote.NewsApi
import com.newsapp.annexnews.domain.model.Article
import com.newsapp.annexnews.domain.repository.NewsRepository
import com.newsapp.annexnews.presentation.notification.sharedpreferences.NotificationPrefs
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NewsNotificationWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params), KoinComponent {
    private val newsRepository: NewsRepository by inject()
    private val newsApi: NewsApi by inject()

    private val notificationPrefs= NotificationPrefs(applicationContext)

    override suspend fun doWork(): Result {
        try {
            // 1. Fetch news data (replace with your actual source list)
            val newArticles = getNewsFromApi(listOf("bbc-news", "cnn"))

            // 2. Store and filter news data (check for new articles)
            val unseenArticles = filterNewArticles(newArticles)

            // 3. Create and send notifications for new articles
            unseenArticles.forEach { article ->
                showNotification(article)
            }

            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    private suspend fun getNewsFromApi(sources: List<String>): List<Article> {
        val response = newsApi.getNews(sources.joinToString(","), 1)
        return response.articles
    }

    private suspend fun filterNewArticles(articles: List<Article>): List<Article> {
        return articles.filter { article ->
            newsRepository.getArticle(article.url) == null
        }
    }

    private fun showNotification(article: Article) {
        val currentTime = System.currentTimeMillis()
        val lastNotificationTime = notificationPrefs.getLastNotificationTime()
        val timeDiff = currentTime - lastNotificationTime

        if (timeDiff >= 15 * 60 * 1000) { // 15 minutes in milliseconds
            createNotificationChannel()
            val notification = NotificationCompat.Builder(applicationContext, "news_channel")
                .setContentTitle(article.title)
                .setContentText(article.description).setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .build()

            try {
                with(NotificationManagerCompat.from(applicationContext)) {
                    notify(article.url.hashCode(), notification)
                }
            } catch (e: SecurityException) {
                Log.e("NewsNotificationWorker", "Error showing notification", e)
            }

            notificationPrefs.saveLastNotificationTime(currentTime)
        } else {
            Log.d("NewsNotificationWorker", "Notification skipped. Not enough time passed since last notification.")
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val name = "News Channel"
                val descriptionText = "Channel for news notifications"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("news_channel", name, importance).apply{
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            } catch (e: SecurityException) {
                Log.e("NewsNotificationWorker", "Error showing notification", e)
            }
        }
    }
}