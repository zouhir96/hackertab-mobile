package com.zrcoding.hackertab.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.dsl.module

/**
* Koin module to provide the DataStore implementation.
*/
internal actual val platformDataStoreModule = module {
    single<DataStore<Preferences>> { AndroidDataStore(context = get()).getDataStore() }
}