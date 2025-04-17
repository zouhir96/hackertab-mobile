package com.zrcoding.hackertab.home.presentation.cards

data class CardViewState<out T>(
    val articles: List<T> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)