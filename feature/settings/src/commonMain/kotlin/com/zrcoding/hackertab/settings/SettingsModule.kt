package com.zrcoding.hackertab.settings

import com.zrcoding.hackertab.settings.presentation.about.SettingsAboutViewModel
import com.zrcoding.hackertab.settings.presentation.appearance.SettingsAppearanceViewModel
import com.zrcoding.hackertab.settings.presentation.master.SettingsMasterViewModel
import com.zrcoding.hackertab.settings.presentation.sources.SettingSourcesViewModel
import com.zrcoding.hackertab.settings.presentation.topics.SettingTopicsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingSourcesViewModel)
    viewModelOf(::SettingTopicsViewModel)
    viewModelOf(::SettingsMasterViewModel)
    viewModelOf(::SettingsAppearanceViewModel)
    viewModelOf(::SettingsAboutViewModel)
}
