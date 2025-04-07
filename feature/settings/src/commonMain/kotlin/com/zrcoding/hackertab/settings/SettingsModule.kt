package com.zrcoding.hackertab.settings

import com.zrcoding.hackertab.di.viewModelDefinition
import com.zrcoding.hackertab.settings.presentation.sources.SettingSourcesScreenViewModel
import com.zrcoding.hackertab.settings.presentation.topics.SettingTopicsScreenViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val settingsModule = module {
    includes(platformModule)
    viewModelDefinition { SettingSourcesScreenViewModel(get()) }
    viewModelDefinition { SettingTopicsScreenViewModel(get()) }
}

/**
 * Provides the platform-specific dependencies.
 */
internal expect val platformModule: Module