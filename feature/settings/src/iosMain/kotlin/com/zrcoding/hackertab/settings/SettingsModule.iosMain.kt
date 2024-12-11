package com.zrcoding.hackertab.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.zrcoding.hackertab.settings.presentation.master.ContactSupport
import com.zrcoding.hackertab.settings.presentation.master.IOSContactSupport
import org.koin.dsl.module

/**
 * Provides the platform-specific dependencies.
 */
internal actual val platformModule = module {
    single< DataStore<Preferences>> { IosDataStore().getDataStore() }
    single<ContactSupport> { IOSContactSupport() }
}