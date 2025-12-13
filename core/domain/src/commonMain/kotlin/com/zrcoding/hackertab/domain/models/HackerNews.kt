package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class HackerNews(
    override val id: String,
    override val title: String,
    override val url: String,
    val time: LocalDateTime,
    val descendants: Long,
    val score: Long
) : BaseArticle()