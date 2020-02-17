package com.supercmmetry.spacesaver.di.modules

import android.content.Context
import android.os.storage.StorageManager
import com.supercmmetry.spacesaver.ds.AnalyticsDataSource
import com.supercmmetry.spacesaver.ds.FileFolderDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val dataSourceModule = module {
    single {
        AnalyticsDataSource(
            androidContext().getSystemService(Context.STORAGE_SERVICE) as StorageManager,
            androidContext()
        )
    }

    single { FileFolderDataSource() }
}