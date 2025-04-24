package com.zrcoding.hackertab.analytics.impl

import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.analytics.models.UserProperties
import dev.gitlive.firebase.analytics.FirebaseAnalytics

/**
 * Implementation of the [AnalyticsHelper] interface for logging events and managing user data
 * on Firebase.
 *
 * @property firebaseAnalytics The Firebase Analytics instance for logging events and user properties.
 */
internal class AnalyticsHelperImpl(
    private val firebaseAnalytics: FirebaseAnalytics,
) : AnalyticsHelper {

    override fun logEvent(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(
            event.name,
            event.properties.toMap()
        )
    }

    override fun identify(userId: String) {

    }

    override fun setUserProperties(properties: UserProperties) {

    }

    override fun clearUserData() {

    }
}