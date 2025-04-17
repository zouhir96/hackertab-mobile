package com.zrcoding.hackertab.home

import com.zrcoding.hackertab.home.presentation.HomeScreenViewModel
import com.zrcoding.hackertab.home.presentation.card.github.GithubCardViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::GithubCardViewModel)
}