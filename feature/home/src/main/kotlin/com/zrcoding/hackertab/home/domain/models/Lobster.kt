package com.zrcoding.hackertab.home.domain.models

import kotlinx.datetime.LocalDateTime

data class Lobster(
    override val id: String,
    val title: String,
    val date: LocalDateTime,
    val commentsCount: Long,
    val reactions: Long,
    val url: String,
    val commentsUrl: String,
) : BaseModel()