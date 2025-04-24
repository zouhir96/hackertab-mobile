package com.zrcoding.hackertab.onboarding.topics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.analytics.models.Param
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.ChipStateHandler
import com.zrcoding.hackertab.design.components.toChipData
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SetupTopicsViewModel(
    private val settingRepository: SettingRepository,
    private val analyticsHelper: AnalyticsHelper
) : ViewModel() {

    private val _viewState = MutableStateFlow(SetupTopicsViewState())
    val viewState = _viewState.asStateFlow()

    private val _goToNextPage = MutableSharedFlow<Unit>()
    val goToNextPage = _goToNextPage.asSharedFlow()

    init {
        viewModelScope.launch {
            val topics = settingRepository.getTopics()
            _viewState.update {
                SetupTopicsViewState(
                    topics = topics.map { it.toChipData() }.toPersistentList()
                )
            }
        }
    }

    fun onChipClicked(topic: ChipData) {
        _viewState.update {
            SetupTopicsViewState(
                topics = ChipStateHandler.handleMultiSelect(
                    currentState = it.topics,
                    clickedChip = topic
                )
            )
        }
    }

    fun onContinueClicked() {
        val selectedTopics = _viewState.value.topics.filter { it.selected }
        viewModelScope.launch {
            settingRepository.saveTopics(ids = selectedTopics.map { it.id })
            trackTopicsSelected(selectedTopics)
            _goToNextPage.emit(Unit)
        }
    }

    private fun trackTopicsSelected(selectedTopics: List<ChipData>) {
        analyticsHelper.logEvent(
            event = AnalyticsEvent(
                name = AnalyticsEvent.Types.TOPICS_SELECTED,
                properties = setOf(
                    Param(
                        key = AnalyticsEvent.ParamKeys.VALUE,
                        value = selectedTopics.map { it.analyticsTag }.toString()
                    )
                )
            )
        )
    }
}