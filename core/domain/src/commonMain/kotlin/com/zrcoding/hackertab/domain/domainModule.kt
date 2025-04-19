package com.zrcoding.hackertab.domain

import com.zrcoding.hackertab.domain.usecases.ObserveSavedSourcesUseCase
import com.zrcoding.hackertab.domain.usecases.ObserveSelectedTopicsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::ObserveSelectedTopicsUseCase)
    factoryOf(::ObserveSavedSourcesUseCase)
}