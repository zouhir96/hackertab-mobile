package com.zrcoding.hackertab.settings.presentation.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SettingsAboutUiState(
    val appVersion: String = "",
    val tourResetDone: Boolean = false,
)

class SettingsAboutViewModel(
    private val settingRepository: SettingRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsAboutUiState())
    val uiState = _uiState.asStateFlow()

    fun resetCoachmarks() {
        viewModelScope.launch {
            settingRepository.resetCoachmarks()
            _uiState.value = SettingsAboutUiState(tourResetDone = true)
        }
    }

    fun consumeTourResetConfirmation() {
        _uiState.value = SettingsAboutUiState(tourResetDone = false)
    }
}
