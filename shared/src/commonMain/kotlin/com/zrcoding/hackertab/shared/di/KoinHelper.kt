package com.zrcoding.hackertab.shared.di

import com.zrcoding.hackertab.data.dataModule
import com.zrcoding.hackertab.domain.domainModule
import com.zrcoding.hackertab.home.homeModule
import com.zrcoding.hackertab.network.networkModule
import com.zrcoding.hackertab.settings.settingsModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Initializes the Koin modules.
 */
fun initKoin() = initKoin(
    module {
        includes(platformModule)
    }
)

/**
 * Initializes the Koin modules.
 *
 * @param appModule the app module to be included
 */
fun initKoin(appModule: Module = module { }) {
    startKoin {
        modules(appModules + appModule + platformModule)
    }
}

internal val appModules = listOf(
    homeModule,
    settingsModule,
    networkModule,
    dataModule,
    domainModule
)

expect val platformModule: Module
