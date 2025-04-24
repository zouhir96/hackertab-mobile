package com.zrcoding.hackertab.analytics

import com.zrcoding.hackertab.analytics.impl.AnalyticsHelperImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import dev.gitlive.firebase.analytics.analytics
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsModule: Module = module {
    single<FirebaseAnalytics> { Firebase.analytics }
    singleOf(::AnalyticsHelperImpl) bind (AnalyticsHelper::class)
}