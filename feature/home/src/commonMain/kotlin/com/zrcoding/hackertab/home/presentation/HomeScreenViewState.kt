package com.zrcoding.hackertab.home.presentation

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.home.domain.models.BaseModel
import com.zrcoding.hackertab.settings.domain.models.Source
import kotlinx.coroutines.flow.Flow

@Stable
sealed interface HomeScreenViewState {
    data object Loading : HomeScreenViewState
    @Stable data class Cards(val cardViewStates: List<CardViewState>) : HomeScreenViewState
}

@Stable
data class CardViewState(
    val source: Source,
    val state: Flow<State>
) {
    @Stable
    sealed interface State {
        data object Loading : State
        data class Success(val articles: List<BaseModel>) : State
        data class Error(val message: String) : State
        data object VerifyConnectionAndRefresh: State
    }
}