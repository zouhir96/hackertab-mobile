package com.zrcoding.hackertab.data.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object SettingsKeys {

    val KEY_SAVED_TOPICS = stringPreferencesKey("saved_topics")
    val KEY_SAVED_SOURCES = stringPreferencesKey("saved_sources")
    val KEY_PROFILE = stringPreferencesKey("user_profile")

    val KEY_THEME_MODE = stringPreferencesKey("theme_mode")
    val KEY_THEME_PALETTE = stringPreferencesKey("theme_palette")
    val KEY_COACHMARKS_SEEN = booleanPreferencesKey("coachmarks_seen")
    val KEY_LAST_VISITED_AT = longPreferencesKey("last_visited_at")
}
