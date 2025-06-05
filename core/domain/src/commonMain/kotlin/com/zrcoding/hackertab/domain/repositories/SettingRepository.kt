package com.zrcoding.hackertab.domain.repositories

import com.zrcoding.hackertab.domain.models.Profile
import com.zrcoding.hackertab.domain.models.Topic
import kotlinx.coroutines.flow.Flow


interface SettingRepository {
    suspend fun getTopics(): List<Topic>

    fun observeSavedTopicsIds(): Flow<List<String>>

    suspend fun saveTopics(id: String)

    suspend fun saveTopics(ids: List<String>)

    suspend fun removeTopic(id: String)

    fun observeSavedSourcesIds(): Flow<List<String>>

    suspend fun saveSource(id: String)

    suspend fun saveSource(ids: List<String>)

    suspend fun removeSource(id: String)

    suspend fun getSavedProfile(): Profile?

    suspend fun getProfiles(): List<Profile>

    suspend fun saveProfile(profile: Profile)
}