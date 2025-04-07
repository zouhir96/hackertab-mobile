package com.zrcoding.hackertab.domain.repositories

import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import kotlinx.coroutines.flow.Flow


interface SettingRepository {
    suspend fun getTopics(): List<Topic>

    fun observeSavedTopicsIds(): Flow<List<String>>

    // TODO Extract this as a use case
    fun observeSelectedTopics(): Flow<List<Topic>>

    suspend fun saveTopic(id: String)

    suspend fun removeTopic(id: String)

    // TODO Extract this as a use case
    fun observeSavedSources(): Flow<List<Source>>

    suspend fun saveSource(id: String)

    suspend fun removeSource(id: String)
}