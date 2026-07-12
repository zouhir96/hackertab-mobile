package com.zrcoding.hackertab.onboarding.coachmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
