package com.zrcoding.hackertab.onboarding.sources

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.icon
import com.zrcoding.hackertab.domain.models.Source
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Stable
data class SetupSourcesViewState(
    val sources: PersistentList<ChipData> = Source.entries.map {
        ChipData(id = it.id, name = it.label, image = it.icon, selected = false)
    }.toPersistentList(),
) {
    fun canContinue() = sources.any { it.selected }
}
