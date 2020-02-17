package com.supercmmetry.spacesaver.di.modules

import com.supercmmetry.spacesaver.repository.AnalyticsRepo
import com.supercmmetry.spacesaver.repository.FileFolderRepo
import org.koin.dsl.module

val repoModule = module {
    single { AnalyticsRepo(get()) }
    single { FileFolderRepo(get()) }
}