package com.zrcoding.hackertab.settings

import com.zrcoding.hackertab.settings.presentation.about.AndroidContactSupport
import com.zrcoding.hackertab.settings.presentation.about.ContactSupport
import org.koin.dsl.module

internal actual val platformModule = module {
    single<ContactSupport> { AndroidContactSupport(context = get()) }
}
