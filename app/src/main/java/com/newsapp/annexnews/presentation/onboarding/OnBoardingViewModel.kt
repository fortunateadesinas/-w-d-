package com.newsapp.annexnews.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.annexnews.domain.usecases.app_entry.SaveAppEntry
import kotlinx.coroutines.launch

class OnBoardingViewModel (
    private val saveAppEntry: SaveAppEntry
): ViewModel() {

    fun onEvent(event: OnBoardingEvent){
        when(event){
            is OnBoardingEvent.SaveAppEntry ->{
                saveUserEntry()
            }
        }
    }

    private fun saveUserEntry() {
        viewModelScope.launch {
            saveAppEntry()
        }
    }

}