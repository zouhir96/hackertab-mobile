package com.zrcoding.hackertab.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.zrcoding.hackertab.analytics.impl.AnalyticsHelperAndroidImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val analyticsModule: Module = module {
    single { FirebaseAnalytics.getInstance(get()) }
    singleOf(::AnalyticsHelperAndroidImpl) bind AnalyticsHelper::class
}