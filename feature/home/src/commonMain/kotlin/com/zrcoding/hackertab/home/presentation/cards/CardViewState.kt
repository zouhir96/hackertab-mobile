package com.zrcoding.hackertab.home.presentation.cards

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class CardWithTopicFilterViewState<out T>(
    val articles: PersistentList<T> = persistentListOf(),
    val topics: PersistentList<CardWithTopicFilterHeaderTopic> = persistentListOf(),
    val selectedTopic: CardWithTopicFilterHeaderTopic,
    val isLoading: Boolean = true,
    val error: String? = null,
    val canRefresh: Boolean = false
)

@Stable
data class CardWithTopicFilterHeaderTopic(
    val id: String,
    val name: String,
    val sourceTag: String?
)

sealed interface CardWithTopicFilterUiEvents {
    object Refresh : CardWithTopicFilterUiEvents
    data class TopicClick(val topic: CardWithTopicFilterHeaderTopic) : CardWithTopicFilterUiEvents
}

data class CardWithoutTopicFilterViewState<out T>(
    val articles: PersistentList<T> = persistentListOf(),
    val isLoading: Boolean = true,
    val error: String? = null,
)
