package com.zrcoding.hackertab.shared.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import com.zrcoding.hackertab.domain.usecases.GetStartDestinationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val startDestinationUseCase: GetStartDestinationUseCase,
    private val settingRepository: SettingRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(AppViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            _viewState.update { it.copy(setupStatus = startDestinationUseCase()) }
        }
        viewModelScope.launch {
            settingRepository.observeThemeMode().collectLatest {
                _viewState.update { state -> state.copy(themeMode = it) }
            }
        }
    }
}