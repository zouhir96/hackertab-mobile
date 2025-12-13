package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class Hashnode(
    override val id: String,
    override val title: String,
    override val url: String,
    val date: LocalDateTime,
    val commentsCount: Long,
    val reactions: Long,
    val tags: List<String>
) : BaseArticle()