package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class Reddit(
    override val id: String,
    override val title: String,
    override val url: String,
    val subreddit: String,
    val score: Long,
    val commentsCount: Long,
    val date: LocalDateTime
) : BaseArticle()