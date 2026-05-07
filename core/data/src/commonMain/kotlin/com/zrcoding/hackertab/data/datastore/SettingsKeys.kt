package com.zrcoding.hackertab.data.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Centralised DataStore preference keys for Hackertab v4.
 *
 * The legacy v3 keys ([KEY_SAVED_TOPICS], [KEY_SAVED_SOURCES], [KEY_PROFILE])
 * still live as `private val`s inside [com.zrcoding.hackertab.data.repositories.SettingRepositoryImpl]
 * for the duration of Wave 0 — they are exposed here to give Wave 3 a single
 * place to import from when it adds new persisted fields. The Wave 3 settings
 * agent migrates the private keys here and removes the duplicates.
 *
 * NEW v4 keys (active):
 *  - [KEY_THEME_MODE]: persists [com.zrcoding.hackertab.domain.models.ThemeMode]
 *    as its enum name (LIGHT / DARK / SYSTEM). Default = SYSTEM.
 *  - [KEY_COACHMARKS_SEEN]: true once the post-onboarding coachmark sequence
 *    has been dismissed at least once. Default = false.
 *  - [KEY_LAST_VISITED_AT]: epoch-millis of the user's last successful Today
 *    feed load. Used for "new since last visit" badges. Default = 0L.
 */
object SettingsKeys {

    /** v3 keys — duplicated here for forward-compat. */
    val KEY_SAVED_TOPICS = stringPreferencesKey("saved_topics")
    val KEY_SAVED_SOURCES = stringPreferencesKey("saved_sources")
    val KEY_PROFILE = stringPreferencesKey("user_profile")

    /** v4 keys. */
    val KEY_THEME_MODE = stringPreferencesKey("theme_mode")
    val KEY_COACHMARKS_SEEN = booleanPreferencesKey("coachmarks_seen")
    val KEY_LAST_VISITED_AT = longPreferencesKey("last_visited_at")
}
