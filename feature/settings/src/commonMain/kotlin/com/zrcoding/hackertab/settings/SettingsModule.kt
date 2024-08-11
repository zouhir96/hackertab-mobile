package com.zrcoding.hackertab.settings

import com.zrcoding.hackertab.di.viewModelDefinition
import com.zrcoding.hackertab.settings.data.repositories.SettingRepositoryImpl
import com.zrcoding.hackertab.settings.domain.repositories.SettingRepository
import com.zrcoding.hackertab.settings.presentation.sources.SettingSourcesScreenViewModel
import com.zrcoding.hackertab.settings.presentation.topics.SettingTopicsScreenViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val USER_PREFERENCES = "user_preferences"

val settingsModule = module {
    includes(platformDataStoreModule)
    singleOf(::SettingRepositoryImpl) bind SettingRepository::class
    viewModelDefinition { SettingSourcesScreenViewModel(get()) }
    viewModelDefinition { SettingTopicsScreenViewModel(get()) }
}

/**
 * Provides the platform-specific dependencies.
 */
internal expect val platformDataStoreModule: Module