package com.zrcoding.hackertab.home.domain.models

import kotlinx.datetime.LocalDateTime

data class Reddit(
    override val id: String,
    val title: String,
    val subreddit: String,
    val url: String,
    val score: Long,
    val commentsCount: Long,
    val date: LocalDateTime
) : BaseModel()