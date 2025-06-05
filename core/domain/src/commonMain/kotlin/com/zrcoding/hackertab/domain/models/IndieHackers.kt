package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class IndieHackers(
    override val id: String,
    val title: String,
    val date: LocalDateTime,
    val commentsCount: Long,
    val reactions: Long,
    val url: String,
) : BaseArticle()