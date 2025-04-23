package com.zrcoding.hackertab.onboarding.topics

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.design.components.ChipData
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class SetupTopicsViewState(
    val topics: PersistentList<ChipData> = persistentListOf(),
) {
    fun canContinue() = topics.any { it.selected }
}
