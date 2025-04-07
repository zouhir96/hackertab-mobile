package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class Medium(
    override val id: String,
    val title: String,
    val date: LocalDateTime,
    val commentsCount: Long,
    val claps: Long,
    val url: String,
) : BaseModel()