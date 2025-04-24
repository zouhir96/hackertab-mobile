package com.zrcoding.hackertab

import android.app.Application
import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.zrcoding.hackertab.settings.presentation.common.AppConfig
import com.zrcoding.hackertab.shared.di.initKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

class Hackertab : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            appModule = module {
                single<Context> { this@Hackertab }
                singleOf(::AppConfigImpl) bind AppConfig::class
            },
        )
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }
}

class AppConfigImpl: AppConfig {
    override val versionName: String
        get() = BuildConfig.VERSION_NAME
}