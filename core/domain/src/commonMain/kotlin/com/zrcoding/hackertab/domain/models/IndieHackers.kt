package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class IndieHackers(
    override val id: String,
    override val title: String,
    override val url: String,
    val date: LocalDateTime,
    val commentsCount: Long,
    val reactions: Long,
) : BaseArticle()