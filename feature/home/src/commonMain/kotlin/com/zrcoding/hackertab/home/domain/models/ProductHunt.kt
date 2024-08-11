package com.zrcoding.hackertab.home.domain.models

data class ProductHunt(
    override val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val commentsCount: Long,
    val reactions: Long,
    val url: String,
    val tags: List<String>
) : BaseModel()