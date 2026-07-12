package com.zrcoding.hackertab.home

import com.zrcoding.hackertab.home.presentation.utils.IOSShareManager
import com.zrcoding.hackertab.home.presentation.utils.ShareManager
import org.koin.dsl.module

internal actual val platformModule = module {
    single<ShareManager> { IOSShareManager() }
}
