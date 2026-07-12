package com.zrcoding.hackertab.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.zrcoding.hackertab.data.datastore.SettingsKeys
import com.zrcoding.hackertab.data.resources.Res
import com.zrcoding.hackertab.domain.models.Profile
import com.zrcoding.hackertab.domain.models.ThemeFont
import com.zrcoding.hackertab.domain.models.ThemeMode
import com.zrcoding.hackertab.domain.models.ThemePalette
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

private const val TOPICS_RES_PATH = "files/topics.json"
private val KEY_SAVED_TOPICS = stringPreferencesKey("saved_topics")
private val KEY_SAVED_SOURCES = stringPreferencesKey("saved_sources")
private val KEY_PROFILE = stringPreferencesKey("user_profile")

class SettingRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : SettingRepository {

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun getTopics(): List<Topic> {
        if (topicsMemoryCache.isNotEmpty()) return topicsMemoryCache

        // TODO Replace this by firebase remote config
        val topicsJson = Res.readBytes(TOPICS_RES_PATH).decodeToString()
        val topics: List<Topic> = Json.decodeFromString<List<Topic>>(topicsJson)
        topicsMemoryCache = topics
        return topics
    }

    override fun observeSavedTopicsIds(): Flow<List<String>> {
        return getSavedIds(KEY_SAVED_TOPICS)
    }

    override suspend fun saveTopics(id: String) {
        saveId(id, KEY_SAVED_TOPICS)
    }

    override suspend fun saveTopics(ids: List<String>) {
        dataStore.edit {
            it[KEY_SAVED_TOPICS] = Json.encodeToString(ids)
        }
    }

    override suspend fun removeTopic(id: String) {
        removeId(id, KEY_SAVED_TOPICS)
    }

    override fun observeSavedSourcesIds(): Flow<List<String>> {
        return getSavedIds(KEY_SAVED_SOURCES)
    }

    override suspend fun saveSource(id: String) {
        saveId(id, KEY_SAVED_SOURCES)
    }

    override suspend fun saveSource(ids: List<String>) {
        dataStore.edit {
            it[KEY_SAVED_SOURCES] = Json.encodeToString(ids)
        }
    }

    override suspend fun removeSource(id: String) {
        removeId(id, KEY_SAVED_SOURCES)
    }

    override suspend fun getSavedProfile(): Profile? {
        return dataStore.data.map {
            it[KEY_PROFILE]?.let { name -> Profile.valueOf(name) }
        }.firstOrNull()
    }

    override suspend fun getProfiles(): List<Profile> {
        return Profile.entries
    }

    override suspend fun saveProfile(profile: Profile) {
        dataStore.edit { it[KEY_PROFILE] = profile.name }
    }

    override fun observeThemeMode(): Flow<ThemeMode> {
        return dataStore.data.map { prefs ->
            val raw = prefs[SettingsKeys.KEY_THEME_MODE]
            if (raw != null) {
                runCatching { ThemeMode.valueOf(raw) }.getOrDefault(ThemeMode.SYSTEM)
            } else {
                ThemeMode.SYSTEM
            }
        }
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.edit { it[SettingsKeys.KEY_THEME_MODE] = mode.name }
    }

    override fun observeThemePalette(): Flow<ThemePalette> {
        return dataStore.data.map { prefs ->
            val raw = prefs[SettingsKeys.KEY_THEME_PALETTE]
            if (raw != null) {
                runCatching { ThemePalette.valueOf(raw) }.getOrDefault(ThemePalette.DEFAULT)
            } else {
                ThemePalette.DEFAULT
            }
        }
    }

    override suspend fun setThemePalette(palette: ThemePalette) {
        dataStore.edit { it[SettingsKeys.KEY_THEME_PALETTE] = palette.name }
    }

    override fun observeThemeFont(): Flow<ThemeFont> {
        return dataStore.data.map { prefs ->
            val raw = prefs[SettingsKeys.KEY_THEME_FONT]
            if (raw != null) {
                runCatching { ThemeFont.valueOf(raw) }.getOrDefault(ThemeFont.GEIST)
            } else {
                ThemeFont.GEIST
            }
        }
    }

    override suspend fun setThemeFont(font: ThemeFont) {
        dataStore.edit { it[SettingsKeys.KEY_THEME_FONT] = font.name }
    }

    override fun observeCoachmarksSeen(): Flow<Boolean> {
        return dataStore.data.map { prefs ->
            prefs[SettingsKeys.KEY_COACHMARKS_SEEN] ?: true
        }
    }

    override suspend fun setCoachmarksSeen(seen: Boolean) {
        dataStore.edit { it[SettingsKeys.KEY_COACHMARKS_SEEN] = seen }
    }

    override suspend fun resetCoachmarks() {
        setCoachmarksSeen(false)
    }

    override suspend fun getLastVisitedAt(): Long {
        return dataStore.data.map { it[SettingsKeys.KEY_LAST_VISITED_AT] ?: 0L }.firstOrNull() ?: 0L
    }

    override suspend fun setLastVisitedAt(epochMillis: Long) {
        dataStore.edit { it[SettingsKeys.KEY_LAST_VISITED_AT] = epochMillis }
    }

    private fun getSavedIds(key: Preferences.Key<String>): Flow<List<String>> {
        return dataStore.data.map { it.fromSavedJsonToList(key) }
    }

    private suspend fun saveId(id: String, key: Preferences.Key<String>) {
        dataStore.edit {
            val newList = it.fromSavedJsonToList(key) + id
            it[key] = Json.encodeToString(newList)
        }
    }

    private suspend fun removeId(id: String, key: Preferences.Key<String>) {
        dataStore.edit {
            val newList = it.fromSavedJsonToList(key) - id
            it[key] = Json.encodeToString(newList)
        }
    }

    private fun Preferences.fromSavedJsonToList(key: Preferences.Key<String>): List<String> {
        val topicsIdsPref = get(key) ?: Json.encodeToString(emptyList<String>())
        return Json.decodeFromString<List<String>>(topicsIdsPref)
    }

    companion object {
        private var topicsMemoryCache = emptyList<Topic>()
    }
}