package com.zrcoding.hackertab.home

import com.zrcoding.hackertab.home.presentation.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    includes(platformModule)
    viewModelOf(::HomeViewModel)
}

internal expect val platformModule: Module