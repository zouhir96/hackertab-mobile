package com.zrcoding.hackertab.settings

import com.zrcoding.hackertab.settings.presentation.sources.SettingSourcesScreenViewModel
import com.zrcoding.hackertab.settings.presentation.topics.SettingTopicsScreenViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    includes(platformModule)
    viewModelOf(::SettingSourcesScreenViewModel)
    viewModelOf(::SettingTopicsScreenViewModel)
}

/**
 * Provides the platform-specific dependencies.
 */
internal expect val platformModule: Module