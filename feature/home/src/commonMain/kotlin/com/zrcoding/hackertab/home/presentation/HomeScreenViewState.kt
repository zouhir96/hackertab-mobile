package com.zrcoding.hackertab.home.presentation

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.domain.models.Source

@Stable
data class HomeScreenViewState(
    val enabledSources: List<Source> = emptyList(),
    val isLoading: Boolean = true
)