package com.zrcoding.hackertab.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Provides the platform-specific dependencies.
 */
internal actual val platformModule: Module = module {
    single<DataStore<Preferences>> { IosDataStore().create() }
}
