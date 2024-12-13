package com.zrcoding.hackertab.settings.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.settings.data.dtos.SourceDto
import com.zrcoding.hackertab.settings.domain.models.Source
import com.zrcoding.hackertab.settings.domain.models.SourceName
import com.zrcoding.hackertab.settings.domain.models.Topic
import com.zrcoding.hackertab.settings.domain.repositories.SettingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

private const val TOPICS_RES_PATH = "files/topics.json"
private val KEY_SAVED_TOPICS = stringPreferencesKey("saved_topics")
private const val SOURCES_RES_PATH = "files/sources.json"
private val KEY_SAVED_SOURCES = stringPreferencesKey("saved_sources")

class SettingRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : SettingRepository {

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun getTopics(): List<Topic> {
        if (topicsMemoryCache.isNotEmpty()) return topicsMemoryCache

        val topicsJson = Res.readBytes(TOPICS_RES_PATH).decodeToString()
        val topics: List<Topic> = Json.decodeFromString<List<Topic>>(topicsJson)
        topicsMemoryCache = topics
        return topics
    }

    override fun getSavedTopicsIds(): Flow<List<String>> {
        return getSavedIds(KEY_SAVED_TOPICS)
    }

    override suspend fun saveTopic(id: String) {
        saveId(id, KEY_SAVED_TOPICS)
    }

    override suspend fun removeTopic(id: String) {
        removeId(id, KEY_SAVED_TOPICS)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeSelectedTopics(): Flow<List<Topic>> {
        return getSavedTopicsIds().mapLatest { savedTopicsIds ->
            getTopics().filter { it.id in savedTopicsIds }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun getSources(): List<Source> {
        if (sourcesMemoryCash.isNotEmpty()) return sourcesMemoryCash

        val sourcesJson = Res.readBytes(SOURCES_RES_PATH).decodeToString()
        val sources: List<Source> = Json.decodeFromString<List<SourceDto>>(
            sourcesJson
        ).map { it.toSource() }
        sourcesMemoryCash = sources
        return sources
    }

    override fun getSavedSourcesNames(): Flow<List<SourceName>> {
        return getSavedIds(KEY_SAVED_SOURCES).map { names ->
            names.mapNotNull { name ->
                SourceName.entries.firstOrNull {
                    it.value == name
                }
            }
        }
    }

    override suspend fun saveSource(id: String) {
        saveId(id, KEY_SAVED_SOURCES)
    }

    override suspend fun removeSource(id: String) {
        removeId(id, KEY_SAVED_SOURCES)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeSelectedSources(): Flow<List<Source>> {
        return getSavedSourcesNames().mapLatest { sourceNames ->
            getSources().filter { it.name in sourceNames }
        }
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
        private var sourcesMemoryCash = emptyList<Source>()
    }
}