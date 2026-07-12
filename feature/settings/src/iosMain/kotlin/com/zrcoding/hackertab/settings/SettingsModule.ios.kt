package com.zrcoding.hackertab.settings

import com.zrcoding.hackertab.settings.presentation.about.ContactSupport
import com.zrcoding.hackertab.settings.presentation.about.IOSContactSupport
import org.koin.dsl.module

internal actual val platformModule = module {
    single<ContactSupport> { IOSContactSupport() }
}
