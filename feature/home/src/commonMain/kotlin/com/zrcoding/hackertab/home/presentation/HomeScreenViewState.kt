package com.zrcoding.hackertab.home.presentation

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class HomeScreenViewState(
    val enabledSources: PersistentList<Source> = persistentListOf(),
    val selectedSource: Source? = null,
    val enabledTopics: PersistentList<Topic> = persistentListOf(),
    val selectedTopic: Topic? = null,
    val articles: PersistentList<BaseArticle> = persistentListOf(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val canRefresh: Boolean = false,
)