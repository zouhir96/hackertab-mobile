package com.zrcoding.hackertab.onboarding.done

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for [OnboardingDoneScreen].
 *
 * On "Open my feed" tap it writes `coachmarks_seen = false` so that the
 * Wave-5 coachmark overlay fires on the first Home feed load, then emits
 * the navigation event.
 *
 * Wave 4 wire-up note: navigate to the Home root destination and clear the
 * back-stack so the user cannot navigate back to onboarding.
 */
class OnboardingDoneViewModel(
    private val settingRepository: SettingRepository,
) : ViewModel() {

    private val _navigateToFeed = MutableSharedFlow<Unit>()
    val navigateToFeed = _navigateToFeed.asSharedFlow()

    fun onOpenFeedClicked() {
        viewModelScope.launch {
            // Critical: set seen = false so Wave-5 coachmarks fire on first Home load.
            settingRepository.setCoachmarksSeen(seen = false)
            _navigateToFeed.emit(Unit)
        }
    }
}
