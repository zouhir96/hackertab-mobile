package com.zrcoding.hackertab.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.home.domain.usecases.GenerateHomeViewStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val generateHomeViewStateUseCase: GenerateHomeViewStateUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<HomeScreenViewState>(HomeScreenViewState.Loading)
    val viewState = _viewState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            generateHomeViewStateUseCase().collectLatest { cards ->
                _viewState.update { HomeScreenViewState.Cards(cards) }
            }
        }
    }
}