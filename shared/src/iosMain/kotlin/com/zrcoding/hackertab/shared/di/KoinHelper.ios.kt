package com.zrcoding.hackertab.shared.di

import com.zrcoding.hackertab.settings.presentation.common.AppConfig
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import platform.Foundation.NSBundle

actual val platformModule: Module = module {
    singleOf<AppConfig>(::AppConfigImpl)
}

class AppConfigImpl: AppConfig {
    override val versionName: String
        get() = NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String ?: "Unknown"
}