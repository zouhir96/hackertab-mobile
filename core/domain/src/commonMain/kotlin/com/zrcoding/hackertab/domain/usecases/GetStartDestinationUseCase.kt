package com.zrcoding.hackertab.domain.usecases

import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.flow.firstOrNull

class GetStartDestinationUseCase(
    private val settingRepository: SettingRepository
) {
    sealed interface Result {
        data object COMPLETED : Result

        data class NotCompleted(val screens: List<ScreenToComplete>) : Result
    }

    sealed interface ScreenToComplete {
        data class ProfileSetup(val newUser: Boolean) : ScreenToComplete
        data object TopicsSetup : ScreenToComplete
        data object SourcesSetup : ScreenToComplete
    }

    suspend operator fun invoke(): Result {

        val result = mutableListOf<ScreenToComplete>()

        val profile = settingRepository.getSavedProfile()
        val topics = settingRepository.observeSavedTopicsIds().firstOrNull()
        val sources = settingRepository.observeSavedSourcesIds().firstOrNull()
        if (profile == null) {
            result.add(
                ScreenToComplete.ProfileSetup(
                    newUser = topics.isNullOrEmpty() && sources.isNullOrEmpty()
                )
            )

            if (topics == null || topics.isEmpty()) result.add(ScreenToComplete.TopicsSetup)

            if (sources == null || sources.isEmpty()) result.add(ScreenToComplete.SourcesSetup)
        }

        return if (result.isEmpty()) Result.COMPLETED else Result.NotCompleted(result)
    }
}