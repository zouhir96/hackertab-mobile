package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class HackerNews(
    override val id: String,
    val title: String,
    val url: String,
    val time: LocalDateTime,
    val descendants: Long,
    val score: Long
) : BaseArticle()