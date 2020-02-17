package com.supercmmetry.spacesaver.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.supercmmetry.spacesaver.data.Analytics
import com.supercmmetry.spacesaver.repository.AnalyticsRepo
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class AnalyticsViewModel(private val analyticsRepo: AnalyticsRepo) : ViewModel() {
    val liveAnalytics = MutableLiveData<List<Analytics>>()

    init {
        GlobalScope.launch {
            while(true) {
                liveAnalytics.postValue(analyticsRepo.getStorageAnalytics())
                delay(10000)
            }
        }
    }
}