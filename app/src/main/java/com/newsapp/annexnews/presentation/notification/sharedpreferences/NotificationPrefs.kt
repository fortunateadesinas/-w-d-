package com.newsapp.annexnews.presentation.notification.sharedpreferences

import android.content.Context

class NotificationPrefs(context: Context) {

    private val PREFS_NAME = "notification_prefs"
    private val KEY_LAST_NOTIFICATION_TIME = "last_notification_time"
    private val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getLastNotificationTime(): Long {
        return sharedPrefs.getLong(KEY_LAST_NOTIFICATION_TIME, 0)
    }

    fun saveLastNotificationTime(time: Long) {
        sharedPrefs.edit().putLong(KEY_LAST_NOTIFICATION_TIME, time).apply()
    }
}