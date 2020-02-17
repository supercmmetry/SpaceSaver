package com.supercmmetry.spacesaver.di.modules

import com.supercmmetry.spacesaver.ui.dashboard.AnalyticsViewModel
import com.supercmmetry.spacesaver.ui.explorer.FileFolderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AnalyticsViewModel(get()) }
    viewModel { FileFolderViewModel(get()) }
}