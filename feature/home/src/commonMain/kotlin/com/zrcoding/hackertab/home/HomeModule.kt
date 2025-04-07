package com.zrcoding.hackertab.home

import com.zrcoding.hackertab.di.viewModelDefinition
import com.zrcoding.hackertab.home.domain.usecases.GenerateHomeViewStateUseCase
import com.zrcoding.hackertab.home.presentation.HomeScreenViewModel
import org.koin.dsl.module

val homeModule = module {
    factory { GenerateHomeViewStateUseCase(settingRepository = get(), articleRepository = get()) }
    viewModelDefinition { HomeScreenViewModel(generateHomeViewStateUseCase = get()) }
}