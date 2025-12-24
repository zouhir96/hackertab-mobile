package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class Article(
    override val id: String,
    override val title: String,
    override val url: String,
    val publishedAt: LocalDateTime,
    val commentsCount: Long,
    val reactions: Long,
    val tags: List<String>,
    val canonicalUrl: String?,
    val imageUrl: String?,
    val source: Source?,
): BaseArticle()
