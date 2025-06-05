package com.zrcoding.hackertab.analytics.impl

import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.analytics.models.UserProperties
import io.github.aakira.napier.Napier

private const val TAG = "StubAnalyticsHelper"

/**
 * Debug AnalyticsHelper which logs events in the console.
 */
internal class AnalyticsHelperStubImpl : AnalyticsHelper {

    override fun logEvent(event: AnalyticsEvent) {
        Napier.d(message = "Log analytics: $event", tag = TAG)
    }

    override fun identify(userId: String) {}

    override fun setUserProperties(properties: UserProperties) {}

    override fun clearUserData() {}
}