package com.zrcoding.hackertab.settings.presentation.topics

import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingTopicsScreenViewModel(
    private val settingRepository: SettingRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(SettingTopicsScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            val topics = settingRepository.getTopics()
            settingRepository.observeSavedTopicsIds().collectLatest { savedTopicsIds ->
                _viewState.update {
                    SettingTopicsScreenViewState(
                        topics = topics.map {
                            ChipData(
                                id = it.id,
                                name = it.label,
                                selected = it.id in savedTopicsIds
                            )
                        }
                    )
                }
            }
        }
    }

    fun onChipClicked(topic: ChipData) {
        viewModelScope.launch {
            if (topic.selected) {
                settingRepository.removeTopic(topic.id)
            } else {
                settingRepository.saveTopic(topic.id)
            }
        }
    }
}