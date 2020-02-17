package com.supercmmetry.spacesaver.ds

import android.content.Context
import android.os.storage.StorageManager
import com.supercmmetry.spacesaver.data.Analytics

class AnalyticsDataSource (private val storageManager: StorageManager, private val context: Context) {
    fun getStorageAnalytics(): List<Analytics> {
        val analyticsList = mutableListOf<Analytics>()
        storageManager.storageVolumes.forEach {
            volume ->
            val analytics = Analytics(volume, context)
            analytics.generate()
            analyticsList.add(analytics)
        }
        return analyticsList
    }
}