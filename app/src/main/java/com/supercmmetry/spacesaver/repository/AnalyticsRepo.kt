package com.supercmmetry.spacesaver.repository
import com.supercmmetry.spacesaver.ds.AnalyticsDataSource

class AnalyticsRepo(private val analyticsDataSource: AnalyticsDataSource) {
    fun getStorageAnalytics() = analyticsDataSource.getStorageAnalytics()

}