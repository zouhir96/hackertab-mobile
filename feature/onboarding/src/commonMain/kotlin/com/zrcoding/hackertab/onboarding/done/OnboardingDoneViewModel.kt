package com.zrcoding.hackertab.onboarding.done

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class OnboardingDoneViewModel(
    private val settingRepository: SettingRepository,
) : ViewModel() {

    private val _navigateToFeed = MutableSharedFlow<Unit>()
    val navigateToFeed = _navigateToFeed.asSharedFlow()

    fun onOpenFeedClicked() {
        viewModelScope.launch {
            settingRepository.setCoachmarksSeen(seen = false)
            _navigateToFeed.emit(Unit)
        }
    }
}
