package com.zrcoding.hackertab

import android.app.Application
import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.zrcoding.hackertab.domain.common.AppConfig
import com.zrcoding.hackertab.shared.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
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
        Napier.base(DebugAntilog())
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }
}

class AppConfigImpl: AppConfig {
    override val versionName: String
        get() = BuildConfig.VERSION_NAME

    override val isDebug: Boolean
        get() = BuildConfig.DEBUG
}