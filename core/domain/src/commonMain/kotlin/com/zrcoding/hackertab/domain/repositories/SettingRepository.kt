package com.zrcoding.hackertab.domain.repositories

import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.SourceName
import com.zrcoding.hackertab.domain.models.Topic
import kotlinx.coroutines.flow.Flow


interface SettingRepository {
    suspend fun getTopics(): List<Topic>

    fun getSavedTopicsIds(): Flow<List<String>>

    suspend fun saveTopic(id: String)
    
    suspend fun removeTopic(id: String)

    fun observeSelectedTopics(): Flow<List<Topic>>

    suspend fun getSources(): List<Source>

    fun getSavedSourcesNames(): Flow<List<SourceName>>

    suspend fun saveSource(id: String)

    suspend fun removeSource(id: String)

    fun observeSelectedSources(): Flow<List<Source>>
}