package com.zrcoding.hackertab.bookmarks.presentation

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.domain.models.BookmarkedArticle
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList

/** How bookmarks are grouped in the list view. */
enum class GroupBy { ALL, BY_SOURCE, BY_DATE }

/** Key used to group bookmark rows when [GroupBy.BY_SOURCE] or [GroupBy.BY_DATE] is active. */
data class GroupKey(val label: String)

@Stable
data class BookmarksViewState(
    /** The full, unfiltered list of all bookmarks (raw from DB). */
    val bookmarks: PersistentList<BookmarkedArticle> = persistentListOf(),
    /**
     * Bookmarks after applying [searchQuery] filter, then grouped by [groupBy].
     * Under [GroupBy.ALL] there is one entry with key `GroupKey("")` holding
     * the complete filtered list.
     */
    val groupedBookmarks: PersistentMap<GroupKey, PersistentList<BookmarkedArticle>> = persistentMapOf(),
    val isLoading: Boolean = true,
    val searchQuery: String = "",
    val groupBy: GroupBy = GroupBy.ALL,
) {
    val totalCount: Int get() = bookmarks.size
    val unreadCount: Int get() = bookmarks.count { !it.read }

    /** Flat list of bookmarks after the search filter is applied (convenience accessor). */
    val filteredBookmarks: PersistentList<BookmarkedArticle>
        get() = groupedBookmarks.values.flatten().toPersistentList()
}

