package com.zrcoding.hackertab.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.zrcoding.hackertab.settings.presentation.master.AndroidContactSupport
import com.zrcoding.hackertab.settings.presentation.master.ContactSupport
import org.koin.dsl.module

/**
* Koin module to provide the DataStore implementation.
*/
internal actual val platformModule = module {
    single<DataStore<Preferences>> { AndroidDataStore(context = get()).getDataStore() }
    single<ContactSupport> { AndroidContactSupport(context = get()) }
}