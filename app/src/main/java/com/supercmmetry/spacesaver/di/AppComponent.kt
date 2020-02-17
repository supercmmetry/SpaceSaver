package com.supercmmetry.spacesaver.di

import com.supercmmetry.spacesaver.di.modules.dataSourceModule
import com.supercmmetry.spacesaver.di.modules.repoModule
import com.supercmmetry.spacesaver.di.modules.viewModelModule

val appComponentModule = listOf (
    viewModelModule,
    repoModule,
    dataSourceModule
)