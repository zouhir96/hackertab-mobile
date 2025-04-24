package com.zrcoding.hackertab.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.staticCompositionLocalOf
import com.zrcoding.hackertab.analytics.impl.AnalyticsHelperStubImpl
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.analytics.models.Param
import com.zrcoding.hackertab.analytics.models.UserProperties
import dev.gitlive.firebase.analytics.FirebaseAnalyticsEvents

/**
 * Interface for handling analytics events and user identification in an application.
 *
 * Provides methods for logging events, identifying users, setting user properties, and clearing user data.
 */
interface AnalyticsHelper {
    /**
     * Logs an analytics event to the specified analytics platforms.
     *
     * @param event The analytics event to be logged, which contains the event name and associated properties.
     * @param where The set of analytics platforms (addons) to which the event should be sent. Defaults to Firebase.
     */
    fun logEvent(
        event: AnalyticsEvent,
    )

    /**
     * Identifies a user in the analytics system by their unique ID.
     *
     * @param userId The unique identifier of the user to be tracked in analytics.
     */
    fun identify(userId: String)

    /**
     * Sets properties related to the user in the analytics system.
     *
     * These properties can be used for user segmentation and analysis.
     *
     * @param properties A set of key-value pairs representing the user's properties.
     */
    fun setUserProperties(properties: UserProperties)

    /**
     * Clears any user-related data from the analytics system.
     *
     * This method should be called when the user logs out or when the data needs to be reset for privacy reasons.
     */
    fun clearUserData()
}

/**
 * Global key used to obtain access to the AnalyticsHelper through a CompositionLocal.
 */
val LocalAnalyticsHelper = staticCompositionLocalOf<AnalyticsHelper> {
    // Provide a default AnalyticsHelper which logs events in the console.
    // This is so that tests and previews do not have to provide one.
    // For real app builds provide the real implementation using dependency injection.
    AnalyticsHelperStubImpl()
}

/**
 * A side-effect which Logs a screen view event using the provided analytics helper.
 *
 * This composable function tracks when a user views a screen and logs the event with specified properties.
 * By default, it logs the screen name and the current language, but additional properties can be added.
 * The event is sent to Firebase analytics.
 *
 * @param screenName The name of the screen being viewed.
 * @param properties Additional parameters to include in the event. Defaults to an empty set.
 * @param analyticsHelper The analytics helper instance used to log the event. Defaults to [LocalAnalyticsHelper.current].
 */
@Composable
fun TrackScreenViewEvent(
    screenName: String,
    properties: Set<Param> = emptySet(),
    analyticsHelper: AnalyticsHelper = LocalAnalyticsHelper.current,
) = DisposableEffect(Unit) {
    val events = AnalyticsEvent(
        name = FirebaseAnalyticsEvents.SCREEN_VIEW,
        properties = setOf(
            Param(AnalyticsEvent.ParamKeys.SCREEN_NAME, screenName),
        ) + properties
    )
    analyticsHelper.logEvent(events)
    onDispose {}
}