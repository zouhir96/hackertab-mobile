package com.zrcoding.hackertab.settings.presentation.appearance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.models.ThemeMode
import com.zrcoding.hackertab.domain.models.ThemePalette
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

    val themePalette: StateFlow<ThemePalette> = settingRepository.observeThemePalette()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ThemePalette.DEFAULT,
        )

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            settingRepository.setThemeMode(mode)
        }
    }

    fun setThemePalette(palette: ThemePalette) {
        viewModelScope.launch {
            settingRepository.setThemePalette(palette)
        }
    }
}
