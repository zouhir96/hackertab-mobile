package com.zrcoding.hackertab.domain.repositories

import com.zrcoding.hackertab.domain.models.Topic
import kotlinx.coroutines.flow.Flow


interface SettingRepository {
    suspend fun getTopics(): List<Topic>

    fun observeSavedTopicsIds(): Flow<List<String>>

    suspend fun saveTopic(id: String)

    suspend fun removeTopic(id: String)

    fun observeSavedSourcesIds(): Flow<List<String>>

    suspend fun saveSource(id: String)

    suspend fun removeSource(id: String)
}