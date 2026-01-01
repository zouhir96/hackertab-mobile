package com.zrcoding.hackertab.onboarding.topics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.analytics.models.Param
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.ChipStateHandler
import com.zrcoding.hackertab.design.components.toChipData
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import com.zrcoding.hackertab.onboarding.SetupTopicsScreen
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SetupTopicsViewModel(
    private val settingRepository: SettingRepository,
    private val analyticsHelper: AnalyticsHelper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val route = savedStateHandle.toRoute<SetupTopicsScreen>()

    private val _viewState = MutableStateFlow(SetupTopicsViewState())
    val viewState = _viewState.asStateFlow()

    private val _goToNextPage = MutableSharedFlow<Unit>()
    val goToNextPage = _goToNextPage.asSharedFlow()

    init {
        viewModelScope.launch {
            val groupedTopics = settingRepository.getTopics()
                .groupBy { it.category ?: "Other" }
                .toMutableMap()

            if (groupedTopics.containsKey("Other")) {
                val other = groupedTopics.remove("Other")!!
                groupedTopics["Other"] = other
            }
            val topics = groupedTopics.map {
                val selected = it.key == route.profile.category
                it.key to it.value.map { topic ->
                    topic.toChipData(selected = selected)
                }.toPersistentList()
            }.toPersistentList()
            _viewState.update { it.copy(topics = topics) }
        }
    }

    fun onChipClicked(topic: ChipData) {
        val newTopics = _viewState.value.topics.map { (category, topics) ->
            category to ChipStateHandler.handleMultiSelect(
                currentState = topics,
                clickedChip = topic
            )
        }.toPersistentList()
        _viewState.update { SetupTopicsViewState(topics = newTopics) }
    }

    fun onContinueClicked() {
        val selectedTopics = _viewState.value.topics.flatMap { (_, topics) ->
            topics.filter { it.selected }
        }
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