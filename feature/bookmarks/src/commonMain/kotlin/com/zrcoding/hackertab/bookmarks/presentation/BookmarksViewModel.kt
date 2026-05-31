package com.zrcoding.hackertab.bookmarks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.models.BookmarkedArticle
import com.zrcoding.hackertab.domain.repositories.BookmarkRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class BookmarksViewModel(
    private val bookmarkRepository: BookmarkRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow(BookmarksViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            bookmarkRepository.observeAllBookmarks().collect { bookmarks ->
                _viewState.update { state ->
                    val immutable = bookmarks.toPersistentList()
                    state.copy(
                        bookmarks = immutable,
                        isLoading = false,
                        groupedBookmarks = computeGrouped(immutable, state.searchQuery, state.groupBy),
                    )
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _viewState.update { state ->
            state.copy(
                searchQuery = query,
                groupedBookmarks = computeGrouped(state.bookmarks, query, state.groupBy),
            )
        }
    }

    fun onGroupByChanged(groupBy: GroupBy) {
        _viewState.update { state ->
            state.copy(
                groupBy = groupBy,
                groupedBookmarks = computeGrouped(state.bookmarks, state.searchQuery, groupBy),
            )
        }
    }

    fun removeBookmark(articleId: String) {
        viewModelScope.launch {
            bookmarkRepository.removeBookmark(articleId)
        }
    }

    fun markRead(articleId: String) {
        viewModelScope.launch {
            bookmarkRepository.markRead(articleId)
        }
    }

    private fun computeGrouped(
        all: PersistentList<BookmarkedArticle>,
        query: String,
        groupBy: GroupBy,
    ): PersistentMap<GroupKey, PersistentList<BookmarkedArticle>> {
        val filtered = if (query.isBlank()) {
            all
        } else {
            all.filter { it.title.contains(query, ignoreCase = true) }.toPersistentList()
        }

        return when (groupBy) {
            GroupBy.ALL -> {
                if (filtered.isEmpty()) {
                    persistentMapOf()
                } else {
                    persistentMapOf(GroupKey("") to filtered)
                }
            }

            GroupBy.BY_SOURCE -> {
                filtered
                    .groupBy { it.source }
                    .map { (source, items) ->
                        GroupKey(source) to items.toPersistentList()
                    }
                    .toMap()
                    .toPersistentMap()
            }

            GroupBy.BY_DATE -> {
                filtered
                    .groupBy { it.savedAt.dateGroupLabel() }
                    .map { (label, items) ->
                        GroupKey(label) to items.toPersistentList()
                    }
                    .toMap()
                    .toPersistentMap()
            }
        }
    }
}

private fun LocalDateTime.dateGroupLabel(): String {
    return "${this.year}-${this.monthNumber.toString().padStart(2, '0')}-${this.dayOfMonth.toString().padStart(2, '0')}"
}
