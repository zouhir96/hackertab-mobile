package com.zrcoding.hackertab.domain.models

data class ProductHunt(
    override val id: String,
    override val title: String,
    override val url: String,
    val description: String,
    val imageUrl: String,
    val commentsCount: Long,
    val reactions: Long,
    val tags: List<String>
) : BaseArticle()