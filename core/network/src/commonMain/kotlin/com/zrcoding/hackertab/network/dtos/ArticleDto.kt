package com.zrcoding.hackertab.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("url") val url: String,
    @SerialName("canonical_url") val canonicalUrl: String? = null,
    @SerialName("published_at") val publishedAt: Long,
    @SerialName("tags") val tags: List<String>? = emptyList(),
    @SerialName("points_count") val pointsCount: Int? = null,
    @SerialName("comments_count") val commentsCount: Int? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("source") val source: String,
    @SerialName("description") val description: String? = null,
)