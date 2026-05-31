package com.zrcoding.hackertab.bookmarks.presentation

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.domain.models.BookmarkedArticle
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList

enum class GroupBy { ALL, BY_SOURCE, BY_DATE }

data class GroupKey(val label: String)

@Stable
data class BookmarksViewState(
    val bookmarks: PersistentList<BookmarkedArticle> = persistentListOf(),
    val groupedBookmarks: PersistentMap<GroupKey, PersistentList<BookmarkedArticle>> = persistentMapOf(),
    val isLoading: Boolean = true,
    val searchQuery: String = "",
    val groupBy: GroupBy = GroupBy.ALL,
) {
    val totalCount: Int get() = bookmarks.size
    val unreadCount: Int get() = bookmarks.count { !it.read }

    val filteredBookmarks: PersistentList<BookmarkedArticle>
        get() = groupedBookmarks.values.flatten().toPersistentList()
}
