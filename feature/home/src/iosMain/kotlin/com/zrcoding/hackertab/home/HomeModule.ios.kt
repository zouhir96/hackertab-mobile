package com.zrcoding.hackertab.home

import com.zrcoding.hackertab.home.presentation.utils.ContactSupport
import com.zrcoding.hackertab.home.presentation.utils.IOSContactSupport
import com.zrcoding.hackertab.home.presentation.utils.IOSShareManager
import com.zrcoding.hackertab.home.presentation.utils.ShareManager
import org.koin.dsl.module

internal actual val platformModule = module {
    single<ContactSupport> { IOSContactSupport() }
    single<ShareManager> { IOSShareManager() }
}