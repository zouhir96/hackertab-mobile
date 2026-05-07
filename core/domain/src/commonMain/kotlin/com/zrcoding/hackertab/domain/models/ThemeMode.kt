package com.zrcoding.hackertab.domain.models

/**
 * User-selectable theme preference. Mirrors the three modes most v4 mobile
 * apps expose. `SYSTEM` defers to the platform's dark-mode setting.
 *
 * Persisted by [com.zrcoding.hackertab.data.datastore.SettingsKeys.KEY_THEME_MODE].
 */
enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM,
}
