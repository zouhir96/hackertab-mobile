package com.zrcoding.hackertab.bookmarks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.repositories.BookmarkRepository
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookmarksScreenViewModel(
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(BookmarksScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            bookmarkRepository.observeAllBookmarks().collect { bookmarks ->
                _viewState.update {
                    it.copy(
                        bookmarks = bookmarks.toPersistentList(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun removeBookmark(articleId: String) {
        viewModelScope.launch {
            bookmarkRepository.removeBookmark(articleId)
        }
    }
}

