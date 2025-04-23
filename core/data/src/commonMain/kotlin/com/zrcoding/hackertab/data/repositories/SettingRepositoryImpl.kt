package com.zrcoding.hackertab.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.zrcoding.hackertab.data.resources.Res
import com.zrcoding.hackertab.domain.models.Profile
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
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