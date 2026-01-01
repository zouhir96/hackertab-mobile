package com.zrcoding.hackertab.onboarding.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.analytics.models.Param
import com.zrcoding.hackertab.domain.models.Profile
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import com.zrcoding.hackertab.onboarding.SetupProfileScreen
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SetupProfileViewModel(
    private val settingsRepository: SettingRepository,
    private val analyticsHelper: AnalyticsHelper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route = savedStateHandle.toRoute<SetupProfileScreen>()

    private val _viewState = MutableStateFlow(SetupProfileViewState())
    val viewState = _viewState.asStateFlow()

    private val _goToNextPage = MutableSharedFlow<Profile>()
    val goToNextPage = _goToNextPage.asSharedFlow()

    init {
        viewModelScope.launch {
            _viewState.update {
                SetupProfileViewState(
                    profiles = settingsRepository.getProfiles().toPersistentList(),
                    newUser = route.newUser
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
            trackProfileSelected(profile)
            _goToNextPage.emit(profile)
        }
    }

    private fun trackProfileSelected(profile: Profile) {
        analyticsHelper.logEvent(
            event = AnalyticsEvent(
                name = AnalyticsEvent.Types.PROFILE_SELECTED,
                properties = setOf(
                    Param(
                        key = AnalyticsEvent.ParamKeys.VALUE,
                        value = profile.analyticsTag
                    )
                )
            )
        )
    }
}
