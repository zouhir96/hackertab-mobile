package com.zrcoding.hackertab.analytics.models

/**
 * Represents an analytics event with a type, name, and associated parameters.
 *
 * @property name The name of the event.
 * @property properties A [Set] of additional parameters associated with the event.
 */
data class AnalyticsEvent(
    val name: String,
    val properties: Set<Param> = emptySet(),
) {
    /**
     * Contains standard event types for analytics.
     */
    object Types {
        const val SCREEN_VIEW = "screen_view"
        // Add more standard event types here
    }

    /**
     * Contains standard parameter keys for analytics events.
     */
    object ParamKeys {
        const val SCREEN_NAME = "screen_name"
        // Add more standard parameter keys here
    }

    /**
     * Contains standard parameter values for analytics events.
     */
    object ParamValues

    /**
     * Contains standard screens names for analytics events.
     */
    object ScreensNames {
        const val HOME = "home"
    }
}
