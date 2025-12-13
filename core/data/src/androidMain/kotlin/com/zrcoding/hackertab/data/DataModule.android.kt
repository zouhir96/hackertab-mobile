package com.zrcoding.hackertab.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.zrcoding.hackertab.database.di.databaseModule
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Provides the platform-specific dependencies.
 */
internal actual val platformModule: Module = module {
    includes(databaseModule)
    single<DataStore<Preferences>> { AndroidDataStore(context = get()).create() }
}