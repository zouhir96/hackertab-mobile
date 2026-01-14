package com.zrcoding.hackertab.settings

import com.zrcoding.hackertab.settings.presentation.sources.SettingSourcesViewModel
import com.zrcoding.hackertab.settings.presentation.topics.SettingTopicsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingSourcesViewModel)
    viewModelOf(::SettingTopicsViewModel)
}