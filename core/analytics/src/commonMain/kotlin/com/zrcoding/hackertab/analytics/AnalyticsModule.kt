package com.zrcoding.hackertab.analytics

import com.zrcoding.hackertab.analytics.impl.AnalyticsHelperImpl
import com.zrcoding.hackertab.analytics.impl.AnalyticsHelperStubImpl
import com.zrcoding.hackertab.domain.common.AppConfig
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import dev.gitlive.firebase.analytics.analytics
import org.koin.core.module.Module
import org.koin.dsl.module

val analyticsModule: Module = module {
    single<FirebaseAnalytics> { Firebase.analytics }
    single<AnalyticsHelper> {
        val appConfig: AppConfig = get()
        if (appConfig.isDebug) {
            AnalyticsHelperStubImpl()
        } else AnalyticsHelperImpl(get())
    }
}