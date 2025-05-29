package com.zrcoding.hackertab.settings

import com.zrcoding.hackertab.settings.presentation.sources.SettingSourcesScreenViewModel
import com.zrcoding.hackertab.settings.presentation.topics.SettingTopicsScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingSourcesScreenViewModel)
    viewModelOf(::SettingTopicsScreenViewModel)
}