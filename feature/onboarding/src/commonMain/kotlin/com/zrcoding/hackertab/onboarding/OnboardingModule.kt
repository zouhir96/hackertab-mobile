package com.zrcoding.hackertab.onboarding

import com.zrcoding.hackertab.onboarding.profile.SetupProfileViewModel
import com.zrcoding.hackertab.onboarding.sources.SetupSourcesViewModel
import com.zrcoding.hackertab.onboarding.topics.SetupTopicsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onboardingModule = module {
    viewModelOf(::SetupProfileViewModel)
    viewModelOf(::SetupTopicsViewModel)
    viewModelOf(::SetupSourcesViewModel)
}