package com.zrcoding.hackertab.home

import com.zrcoding.hackertab.home.presentation.HomeScreenViewModel
import com.zrcoding.hackertab.home.presentation.cards.conferences.ConferencesCardViewModel
import com.zrcoding.hackertab.home.presentation.cards.devto.DevtoCardViewModel
import com.zrcoding.hackertab.home.presentation.cards.freecodecamp.FreeCodeCampCardViewModel
import com.zrcoding.hackertab.home.presentation.cards.github.GithubCardViewModel
import com.zrcoding.hackertab.home.presentation.cards.hackernews.HackerNewsCardViewModel
import com.zrcoding.hackertab.home.presentation.cards.hashnode.HashnodeCardViewModel
import com.zrcoding.hackertab.home.presentation.cards.indiehackers.IndieHackersCardViewModel
import com.zrcoding.hackertab.home.presentation.cards.lobsters.LobstersViewModel
import com.zrcoding.hackertab.home.presentation.cards.mediun.MediumCardViewModel
import com.zrcoding.hackertab.home.presentation.cards.producthunt.ProductHuntCardViewModel
import com.zrcoding.hackertab.home.presentation.cards.reddit.RedditCardViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    includes(platformModule)
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::GithubCardViewModel)
    viewModelOf(::RedditCardViewModel)
    viewModelOf(::FreeCodeCampCardViewModel)
    viewModelOf(::ConferencesCardViewModel)
    viewModelOf(::DevtoCardViewModel)
    viewModelOf(::HashnodeCardViewModel)
    viewModelOf(::MediumCardViewModel)
    viewModelOf(::HackerNewsCardViewModel)
    viewModelOf(::ProductHuntCardViewModel)
    viewModelOf(::IndieHackersCardViewModel)
    viewModelOf(::LobstersViewModel)
}

internal expect val platformModule: Module