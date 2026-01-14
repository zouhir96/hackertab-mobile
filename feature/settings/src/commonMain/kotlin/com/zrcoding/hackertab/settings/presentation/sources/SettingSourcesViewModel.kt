package com.zrcoding.hackertab.settings.presentation.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.analytics.models.Param
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.toChipData
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import com.zrcoding.hackertab.domain.usecases.ObserveSelectedSourcesUseCase
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingSourcesViewModel(
    private val settingRepository: SettingRepository,
    private val observeSelectedSourcesUseCase: ObserveSelectedSourcesUseCase,
    private val analyticsHelper: AnalyticsHelper
) : ViewModel() {

    private val _viewState = MutableStateFlow<PersistentList<ChipData>>(persistentListOf())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            val sources = Source.entries
            observeSelectedSourcesUseCase().collectLatest { ids->
                _viewState.update {
                    sources.map { it.toChipData(selected = it in ids) }.toPersistentList()
                }
            }
        }
    }

    fun onChipClicked(source: ChipData) {
        if (_viewState.value.count { it.selected } <= 1 && source.selected) return
        viewModelScope.launch {
            if (source.selected) {
                settingRepository.removeSource(source.id)
            } else {
                settingRepository.saveSource(source.id)
            }
            trackSourceSelectionChanged(source)
        }
    }

    fun trackSourceSelectionChanged(source: ChipData) {
        analyticsHelper.logEvent(
            event = AnalyticsEvent(
                name = AnalyticsEvent.Types.SOURCE_SELECTION_CHANGED,
                properties = setOf(
                    Param(
                        key = AnalyticsEvent.ParamKeys.SOURCE_ID,
                        value = source.id
                    ),
                    Param(
                        key = AnalyticsEvent.ParamKeys.VALUE,
                        value = source.selected.not().toString()
                    )
                )
            )
        )
    }
}