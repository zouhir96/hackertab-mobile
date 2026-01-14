package com.zrcoding.hackertab.home

import com.zrcoding.hackertab.home.presentation.utils.AndroidContactSupport
import com.zrcoding.hackertab.home.presentation.utils.AndroidShareManager
import com.zrcoding.hackertab.home.presentation.utils.ContactSupport
import com.zrcoding.hackertab.home.presentation.utils.ShareManager
import org.koin.dsl.module

internal actual val platformModule = module {
    single<ContactSupport> { AndroidContactSupport(context = get()) }
    single<ShareManager> { AndroidShareManager(context = get()) }
}