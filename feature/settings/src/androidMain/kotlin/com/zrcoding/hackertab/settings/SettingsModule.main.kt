package com.zrcoding.hackertab.settings

import com.zrcoding.hackertab.settings.presentation.master.AndroidContactSupport
import com.zrcoding.hackertab.settings.presentation.master.ContactSupport
import org.koin.dsl.module

/**
* Koin module to provide the DataStore implementation.
*/
internal actual val platformModule = module {
    single<ContactSupport> { AndroidContactSupport(context = get()) }
}