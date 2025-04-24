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
internal class AnalyticsHelperIosImpl(
    private val firebaseAnalytics: FirebaseAnalytics,
) : AnalyticsHelper {

    override fun logEvent(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(
            event.name,
            event.properties.toMap()
        )
    }

    override fun identify(userId: String) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun setUserProperties(properties: UserProperties) {
        firebaseAnalytics.setUserProperty(AnalyticsEvent.ParamKeys.NAME, properties.name)
        firebaseAnalytics.setUserProperty(AnalyticsEvent.ParamKeys.EMAIL, properties.email)
        firebaseAnalytics.setUserProperty(AnalyticsEvent.ParamKeys.PHONE, properties.phone)
    }

    override fun clearUserData() {
        firebaseAnalytics.setUserId(null)
        firebaseAnalytics.setUserProperty(AnalyticsEvent.ParamKeys.NAME, "")
        firebaseAnalytics.setUserProperty(AnalyticsEvent.ParamKeys.EMAIL, "")
        firebaseAnalytics.setUserProperty(AnalyticsEvent.ParamKeys.PHONE, "")
    }
}