package com.newsapp.annexnews.domain.usecases.app_entry

import com.newsapp.annexnews.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}