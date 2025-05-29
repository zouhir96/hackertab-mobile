package com.zrcoding.hackertab.domain.usecases

import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class ObserveSavedSourcesUseCase(
    private val settingRepository: SettingRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<Source>> {
        return settingRepository.observeSavedSourcesIds().mapLatest { ids ->
            Source.entries.filter { it.id in ids }
        }
    }
}