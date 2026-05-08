package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class BookmarkedArticle(
    val id: String,
    val title: String,
    val url: String,
    val savedAt: LocalDateTime,
    val source: String,
    /** Whether the user has already read/opened this bookmark. Default: false (unread). */
    val read: Boolean = false,
)

