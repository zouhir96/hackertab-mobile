package com.zrcoding.hackertab.settings

import org.koin.dsl.module

/**
 * Provides the platform-specific dependencies.
 */
internal actual val platformDataStoreModule = module {
    single { IosDataStore().getDataStore() }
}