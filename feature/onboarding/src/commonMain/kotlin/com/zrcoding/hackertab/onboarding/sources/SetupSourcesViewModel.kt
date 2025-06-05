package com.zrcoding.hackertab.onboarding.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.analytics.models.Param
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.ChipStateHandler
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SetupSourcesViewModel(
    private val settingRepository: SettingRepository,
    private val analyticsHelper: AnalyticsHelper
) : ViewModel() {

    private val _viewState = MutableStateFlow(SetupSourcesViewState())
    val viewState = _viewState.asStateFlow()

    private val _goToNextPage = MutableSharedFlow<Unit>()
    val goToNextPage = _goToNextPage.asSharedFlow()

    fun onChipClicked(source: ChipData) {
        _viewState.update {
            SetupSourcesViewState(
                sources = ChipStateHandler.handleMultiSelect(
                    currentState = it.sources,
                    clickedChip = source
                )
            )
        }
    }

    fun onContinueClicked() {
        val selectedSources = _viewState.value.sources.filter { it.selected }
        viewModelScope.launch {
            settingRepository.saveSource(ids = selectedSources.map { it.id })
            trackSourcesSelected(selectedSources)
            _goToNextPage.emit(Unit)
        }
    }

    private fun trackSourcesSelected(selectedSources: List<ChipData>) {
        analyticsHelper.logEvent(
            event = AnalyticsEvent(
                name = AnalyticsEvent.Types.SOURCES_SELECTED,
                properties = setOf(
                    Param(
                        key = AnalyticsEvent.ParamKeys.VALUE,
                        value = selectedSources.map { it.analyticsTag }.toString()
                    )
                )
            )
        )
    }
}