package com.zrcoding.hackertab.settings.presentation.appearance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.models.ThemeMode
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsAppearanceViewModel(
    private val settingRepository: SettingRepository,
) : ViewModel() {

    val themeMode: StateFlow<ThemeMode> = settingRepository.observeThemeMode()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ThemeMode.SYSTEM,
        )

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            settingRepository.setThemeMode(mode)
        }
    }
}
