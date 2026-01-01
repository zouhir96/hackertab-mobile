package com.zrcoding.hackertab.bookmarks.presentation

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.domain.models.BookmarkedArticle
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class BookmarksViewState(
    val bookmarks: PersistentList<BookmarkedArticle> = persistentListOf(),
    val isLoading: Boolean = true
)

