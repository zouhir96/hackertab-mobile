package com.zrcoding.hackertab.data

import com.zrcoding.hackertab.data.repositories.ArticleRepositoryImpl
import com.zrcoding.hackertab.data.repositories.BookmarkRepositoryImpl
import com.zrcoding.hackertab.data.repositories.SettingRepositoryImpl
import com.zrcoding.hackertab.database.di.databaseModule
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.repositories.BookmarkRepository
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    includes(platformModule + databaseModule)
    singleOf(::ArticleRepositoryImpl) bind ArticleRepository::class
    singleOf(::SettingRepositoryImpl) bind SettingRepository::class
    singleOf(::BookmarkRepositoryImpl) bind BookmarkRepository::class
}

/**
 * Provides the platform-specific dependencies.
 */
internal expect val platformModule: Module