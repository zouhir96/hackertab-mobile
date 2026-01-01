package com.zrcoding.hackertab.bookmarks

import com.zrcoding.hackertab.bookmarks.presentation.BookmarksViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val bookmarksModule = module {
    viewModelOf(::BookmarksViewModel)
}

