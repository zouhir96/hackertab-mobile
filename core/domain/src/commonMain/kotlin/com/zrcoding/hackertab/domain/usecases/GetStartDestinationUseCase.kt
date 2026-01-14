package com.zrcoding.hackertab.domain.usecases

import com.zrcoding.hackertab.domain.models.Profile
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.flow.firstOrNull

class GetStartDestinationUseCase(
    private val settingRepository: SettingRepository
) {
    data class Result(
        val profile: Profile?,
        val topicsSetup: Boolean,
        val sourcesSetup: Boolean
    )

    suspend operator fun invoke(): Result {
        val profile = settingRepository.getSavedProfile()
        val topics = settingRepository.observeSavedTopicsIds().firstOrNull()
        val sources = settingRepository.observeSavedSourcesIds().firstOrNull()

        return Result(
            profile = profile,
            topicsSetup = topics.isNullOrEmpty(),
            sourcesSetup = sources.isNullOrEmpty()
        )
    }
}