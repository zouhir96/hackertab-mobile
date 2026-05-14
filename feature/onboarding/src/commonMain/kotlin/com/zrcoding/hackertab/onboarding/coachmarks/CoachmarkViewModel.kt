package com.zrcoding.hackertab.onboarding.coachmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Wave 5K — observes/updates the `coachmarksSeen` flag from [SettingRepository].
 *
 * `initialValue = true` is critical: we don't want the overlay to flash before
 * the first emission from datastore. The post-onboarding writer
 * (OnboardingDoneViewModel) flips it to `false` only when a brand-new user
 * just finished onboarding.
 */
class CoachmarkViewModel(
    private val settingRepository: SettingRepository,
) : ViewModel() {

    val coachmarksSeen: StateFlow<Boolean> = settingRepository
        .observeCoachmarksSeen()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = true,
        )

    fun dismiss() {
        viewModelScope.launch {
            settingRepository.setCoachmarksSeen(seen = true)
        }
    }
}
