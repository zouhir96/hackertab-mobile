package com.zrcoding.hackertab.onboarding.topics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.ChipStateHandler
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SetupTopicsViewModel(
    private val settingRepository: SettingRepository
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
                    topics = topics.map {
                        ChipData(id = it.id, name = it.label, selected = false)
                    }.toPersistentList()
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
        val topicsIds = _viewState.value.topics.filter { it.selected }.map { it.id }
        viewModelScope.launch {
            settingRepository.saveTopics(ids = topicsIds)
            _goToNextPage.emit(Unit)
        }
    }
}