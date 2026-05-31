package com.zrcoding.hackertab.home.presentation

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.SourceLoadState
import com.zrcoding.hackertab.domain.models.Topic
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList

enum class DayBucket(val label: String) {
    TODAY("Today"),
    YESTERDAY("Yesterday"),
    EARLIER_THIS_WEEK("Earlier this week"),
    OLDER("Older"),
}

@Stable
data class HomeViewState(
    val activeSourceId: String = "all",
    val enabledSources: PersistentList<Source> = persistentListOf(),
    val canAddSource: Boolean = false,
    val enabledTopics: PersistentList<Topic> = persistentListOf(),
    val selectedTopic: Topic? = null,
    val canAddTopic: Boolean = false,
    val articlesByDay: PersistentMap<DayBucket, PersistentList<BaseArticle>> = persistentMapOf(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val canRefresh: Boolean = false,
    val seenArticleIds: PersistentList<String> = persistentListOf(),
    val longPressedArticle: BaseArticle? = null,
    val perSourceLoadState: PersistentMap<Source, SourceLoadState> = persistentMapOf(),
    val isPartialReveal: Boolean = false,
) {
    val isAllSourcesMode: Boolean get() = activeSourceId == "all"

    val activeSource: Source? get() = Source.fromId(activeSourceId)

    val showTopicStrip: Boolean
        get() = isAllSourcesMode || (activeSource?.supportsFilters == true)

    val allArticles: PersistentList<BaseArticle>
        get() {
            val list = mutableListOf<BaseArticle>()
            DayBucket.entries.forEach { bucket ->
                articlesByDay[bucket]?.let { list.addAll(it) }
            }
            return list.toPersistentList()
        }
}
