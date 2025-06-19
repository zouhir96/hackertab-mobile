package com.zrcoding.hackertab.domain.usecases

import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class ObserveSelectedTopicsUseCase(
    private val settingRepository: SettingRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<Topic>> {
        return settingRepository.observeSavedTopicsIds().mapLatest { savedTopicsIds ->
            val topics = settingRepository.getTopics()
            listOf(Topic.trending) + topics.filter { it.id in savedTopicsIds }
        }
    }
}