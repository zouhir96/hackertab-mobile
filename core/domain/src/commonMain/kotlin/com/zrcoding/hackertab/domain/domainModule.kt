package com.zrcoding.hackertab.domain

import com.zrcoding.hackertab.domain.usecases.GetStartDestinationUseCase
import com.zrcoding.hackertab.domain.usecases.ObserveBookmarkedIdsUseCase
import com.zrcoding.hackertab.domain.usecases.ObserveSelectedSourcesUseCase
import com.zrcoding.hackertab.domain.usecases.ObserveSelectedTopicsUseCase
import com.zrcoding.hackertab.domain.usecases.ToggleBookmarkUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::ObserveSelectedTopicsUseCase)
    factoryOf(::ObserveSelectedSourcesUseCase)
    factoryOf(::GetStartDestinationUseCase)
    factoryOf(::ToggleBookmarkUseCase)
    factoryOf(::ObserveBookmarkedIdsUseCase)
}