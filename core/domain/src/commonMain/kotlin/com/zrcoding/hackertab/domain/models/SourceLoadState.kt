package com.zrcoding.hackertab.domain.models

sealed interface SourceLoadState {
    data object Idle : SourceLoadState

    data object Loading : SourceLoadState

    data class Loaded(val articleCount: Int) : SourceLoadState

    data class Failed(val error: NetworkErrors) : SourceLoadState
}
