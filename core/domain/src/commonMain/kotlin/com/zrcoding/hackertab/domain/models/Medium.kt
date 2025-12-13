package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class Medium(
    override val id: String,
    override val title: String,
    override val url: String,
    val date: LocalDateTime,
    val commentsCount: Long,
    val claps: Long,
) : BaseArticle()