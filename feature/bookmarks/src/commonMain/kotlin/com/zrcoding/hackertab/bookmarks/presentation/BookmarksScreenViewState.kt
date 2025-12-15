package com.zrcoding.hackertab.bookmarks.presentation

import com.zrcoding.hackertab.domain.models.BookmarkedArticle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BookmarksScreenViewState(
    val bookmarks: ImmutableList<BookmarkedArticle> = persistentListOf(),
    val isLoading: Boolean = true
)

