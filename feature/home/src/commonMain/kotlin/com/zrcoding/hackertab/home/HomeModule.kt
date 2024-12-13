package com.zrcoding.hackertab.home

import com.zrcoding.hackertab.di.viewModelDefinition
import com.zrcoding.hackertab.home.data.repositories.ArticleRepositoryImpl
import com.zrcoding.hackertab.home.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.home.domain.usecases.GenerateHomeViewStateUseCase
import com.zrcoding.hackertab.home.presentation.HomeScreenViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    singleOf(::ArticleRepositoryImpl) bind ArticleRepository::class
    factory { GenerateHomeViewStateUseCase(settingRepository = get(), articleRepository = get()) }
    viewModelDefinition { HomeScreenViewModel(generateHomeViewStateUseCase = get()) }
}