package com.zrcoding.hackertab.settings.presentation.master

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.models.Profile
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.ThemeMode
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SettingsMasterUiState(
    val profile: Profile? = null,
    val topicsCount: Int = 0,
    val sourcesCount: Int = 0,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
)

class SettingsMasterViewModel(
    private val settingRepository: SettingRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsMasterUiState())
    val uiState: StateFlow<SettingsMasterUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                _uiState.update { it.copy(profile = settingRepository.getSavedProfile()) }
            }
            launch {
                settingRepository.observeSavedTopicsIds().collectLatest { ids ->
                    _uiState.update { it.copy(topicsCount = ids.size) }
                }
            }
            launch {
                settingRepository.observeSavedSourcesIds().collectLatest { ids ->
                    _uiState.update { it.copy(sourcesCount = ids.size) }
                }
            }
            launch {
                settingRepository.observeThemeMode().collectLatest { mode ->
                    _uiState.update { it.copy(themeMode = mode) }
                }
            }
        }
    }
}
