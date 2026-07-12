package com.zrcoding.hackertab.domain.repositories

import com.zrcoding.hackertab.domain.models.Profile
import com.zrcoding.hackertab.domain.models.ThemeFont
import com.zrcoding.hackertab.domain.models.ThemeMode
import com.zrcoding.hackertab.domain.models.ThemePalette
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

    fun observeThemeMode(): Flow<ThemeMode>

    suspend fun setThemeMode(mode: ThemeMode)

    fun observeThemePalette(): Flow<ThemePalette>

    suspend fun setThemePalette(palette: ThemePalette)

    fun observeThemeFont(): Flow<ThemeFont>

    suspend fun setThemeFont(font: ThemeFont)

    fun observeCoachmarksSeen(): Flow<Boolean>

    suspend fun setCoachmarksSeen(seen: Boolean)

    suspend fun resetCoachmarks()

    suspend fun getLastVisitedAt(): Long

    suspend fun setLastVisitedAt(epochMillis: Long)
}