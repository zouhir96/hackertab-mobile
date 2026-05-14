package com.zrcoding.hackertab.home.presentation

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList

/** Temporal bucket used to group feed items in the Today screen. */
enum class DayBucket(val label: String) {
    TODAY("Today"),
    YESTERDAY("Yesterday"),
    EARLIER_THIS_WEEK("Earlier this week"),
    OLDER("Older"),
}

@Stable
data class HomeViewState(
    /**
     * "all" = aggregated multi-source Today feed.
     * Any other value = a [Source.id] for the focused-feed mode.
     */
    val activeSourceId: String = "all",
    val enabledSources: PersistentList<Source> = persistentListOf(),
    val canAddSource: Boolean = false,
    val enabledTopics: PersistentList<Topic> = persistentListOf(),
    val selectedTopic: Topic? = null,
    val canAddTopic: Boolean = false,
    /**
     * Feed items grouped by day bucket. Populated after a successful fetch.
     * Empty map → no items (triggers EmptyState).
     */
    val articlesByDay: PersistentMap<DayBucket, PersistentList<BaseArticle>> = persistentMapOf(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val canRefresh: Boolean = false,
    /** Set of article IDs the user has seen in this session (for read-state rendering). */
    val seenArticleIds: PersistentList<String> = persistentListOf(),
    /** article shown in long-press bottom sheet (null = sheet hidden). */
    val longPressedArticle: BaseArticle? = null,
) {
    /** True when the active source is the aggregated "All" view. */
    val isAllSourcesMode: Boolean get() = activeSourceId == "all"

    /** The [Source] corresponding to [activeSourceId], or null when in All mode. */
    val activeSource: Source? get() = Source.fromId(activeSourceId)

    /**
     * Whether the topic strip should be shown. Shown in "all" mode (multi-source
     * aggregation) and for individual sources that support topic filtering.
     */
    val showTopicStrip: Boolean
        get() = isAllSourcesMode || (activeSource?.supportsFilters == true)

    /** Flat list of all articles across all buckets, preserving bucket order. */
    val allArticles: PersistentList<BaseArticle>
        get() {
            val list = mutableListOf<BaseArticle>()
            DayBucket.entries.forEach { bucket ->
                articlesByDay[bucket]?.let { list.addAll(it) }
            }
            return list.toPersistentList()
        }
}
