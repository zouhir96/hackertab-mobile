package com.zrcoding.hackertab.onboarding.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.models.Profile
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SetupProfileViewModel(
    private val settingsRepository: SettingRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(SetupProfileViewState())
    val viewState = _viewState.asStateFlow()

    private val _goToNextPage = MutableSharedFlow<Unit>()
    val goToNextPage = _goToNextPage.asSharedFlow()

    init {
        viewModelScope.launch {
            _viewState.update {
                SetupProfileViewState(
                    profiles = settingsRepository.getProfiles().toPersistentList()
                )
            }
        }
    }

    fun onProfileSelected(profile: Profile) {
        _viewState.update { it.copy(selectedProfile = profile) }
    }

    fun onContinueClicked() {
        val profile = _viewState.value.selectedProfile ?: return
        viewModelScope.launch {
            settingsRepository.saveProfile(profile = profile)
            _goToNextPage.emit(Unit)
        }
    }
}
