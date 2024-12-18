package com.zrcoding.hackertab.settings.presentation.sources

import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.settings.domain.repositories.SettingRepository
import com.zrcoding.hackertab.settings.presentation.common.icon
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingSourcesScreenViewModel(
    private val settingRepository: SettingRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(SettingSourcesScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            val sources = settingRepository.getSources()
            settingRepository.getSavedSourcesNames().collectLatest { ids->
                _viewState.update {
                    SettingSourcesScreenViewState(
                        sources = sources.map {
                            ChipData(
                                id = it.name.value,
                                name = it.label,
                                image = it.icon,
                                selected = it.name in ids
                            )
                        }
                    )
                }
            }
        }
    }

    fun onChipClicked(chipData: ChipData) {
        viewModelScope.launch {
            if (chipData.selected) {
                settingRepository.removeSource(chipData.id)
            } else settingRepository.saveSource(chipData.id)
        }
    }
}