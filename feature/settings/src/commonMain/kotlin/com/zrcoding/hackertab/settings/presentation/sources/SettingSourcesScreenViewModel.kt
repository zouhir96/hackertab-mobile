package com.zrcoding.hackertab.settings.presentation.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.icon
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import com.zrcoding.hackertab.domain.usecases.ObserveSavedSourcesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingSourcesScreenViewModel(
    private val settingRepository: SettingRepository,
    private val observeSavedSourcesUseCase: ObserveSavedSourcesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(SettingSourcesScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            val sources = Source.entries
            observeSavedSourcesUseCase().collectLatest { ids->
                _viewState.update {
                    SettingSourcesScreenViewState(
                        sources = sources.map {
                            ChipData(
                                id = it.id,
                                name = it.label,
                                image = it.icon,
                                selected = it in ids
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