package com.zrcoding.hackertab.settings.presentation.topics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.analytics.models.Param
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.toChipData
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingTopicsViewModel(
    private val settingRepository: SettingRepository,
    private val analyticsHelper: AnalyticsHelper
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<PersistentList<Pair<String, PersistentList<ChipData>>>>(persistentListOf())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            val topics = settingRepository.getTopics()
            settingRepository.observeSavedTopicsIds().collectLatest { savedTopicsIds ->
                val groupedTopics = topics
                    .groupBy { it.category ?: "Other" }
                    .toMutableMap()

                if (groupedTopics.containsKey("Other")) {
                    val other = groupedTopics.remove("Other")!!
                    groupedTopics["Other"] = other
                }
                _viewState.update {
                    groupedTopics.map {
                        it.key to it.value.map { topic ->
                            topic.toChipData(selected = topic.value in savedTopicsIds)
                        }.toPersistentList()
                    }.toPersistentList()
                }
            }
        }
    }

    fun onChipClicked(topic: ChipData) {
        // if (_viewState.value.count { it.selected } <= 1 && topic.selected) return
        viewModelScope.launch {
            if (topic.selected) {
                settingRepository.removeTopic(id = topic.id)
            } else {
                settingRepository.saveTopics(id = topic.id)
            }
            trackTopicSelectionChanged(topic)
        }
    }

    fun trackTopicSelectionChanged(topic: ChipData) {
        analyticsHelper.logEvent(
            event = AnalyticsEvent(
                name = AnalyticsEvent.Types.TOPIC_SELECTION_CHANGED,
                properties = setOf(
                    Param(
                        key = AnalyticsEvent.ParamKeys.TOPIC_ID,
                        value = topic.id
                    ),
                    Param(
                        key = AnalyticsEvent.ParamKeys.VALUE,
                        value = topic.selected.not().toString()
                    )
                )
            )
        )
    }
}