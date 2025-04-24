package com.zrcoding.hackertab.onboarding.sources

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.toChipData
import com.zrcoding.hackertab.domain.models.Source
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Stable
data class SetupSourcesViewState(
    val sources: PersistentList<ChipData> = Source.entries.map { it.toChipData() }
        .toPersistentList(),
) {
    fun canContinue() = sources.any { it.selected }
}
