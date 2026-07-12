package com.zrcoding.hackertab.home.presentation

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class HomeViewState(
    val activeSourceId: String = "",
    val enabledSources: PersistentList<Source> = persistentListOf(),
    val canAddSource: Boolean = false,
    val enabledTopics: PersistentList<Topic> = persistentListOf(),
    val selectedTopic: Topic? = null,
    val canAddTopic: Boolean = false,
    val articles: PersistentList<BaseArticle> = persistentListOf(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val canRefresh: Boolean = false,
    val seenArticleIds: PersistentList<String> = persistentListOf(),
    val longPressedArticle: BaseArticle? = null,
) {
    val activeSource: Source? get() = Source.fromId(activeSourceId)

    val showTopicStrip: Boolean
        get() = activeSource?.supportsFilters == true

    val needsTopicSetup: Boolean
        get() = showTopicStrip && enabledTopics.isEmpty()
}
