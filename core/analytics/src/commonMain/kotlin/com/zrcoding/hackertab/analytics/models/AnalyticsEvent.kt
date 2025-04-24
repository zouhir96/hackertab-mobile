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
        const val PROFILE_SELECTED = "profile_selected"
        const val SOURCES_SELECTED = "sources_selected"
        const val TOPICS_SELECTED = "topics_selected"
        const val SETUP_COMPLETED = "setup_completed"
        const val TOPIC_SELECTION_CHANGED = "topic_selection_changed"
        const val SOURCE_SELECTION_CHANGED = "source_selection_changed"
        const val TOPIC_FILTER_CHANGED = "topic_filter_changed"
        // Add more standard event types here
    }

    /**
     * Contains standard parameter keys for analytics events.
     */
    object ParamKeys {
        const val SCREEN_NAME = "screen_name"
        const val VALUE = "value"
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
        const val SETUP_PROFILE = "SetupProfile"
        const val SETUP_TOPICS = "SetupTopics"
        const val SETUP_SOURCES = "SetupSources"
        const val HOME = "home"
        const val SETTINGS_MASTER = "SettingsMaster"
        const val SETTINGS_TOPICS = "SettingsTopics"
        const val SETTINGS_SOURCES = "SettingsSources"
        // Add more standard screen names here
    }
}
