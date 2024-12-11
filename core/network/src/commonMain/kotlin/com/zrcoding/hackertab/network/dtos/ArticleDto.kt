package com.zrcoding.hackertab.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    @SerialName("id") val id: String,
    @SerialName("url") val url: String,
    @SerialName("title") val title: String,
    @SerialName("published_at") val publishedAt: Long,
    @SerialName("tags") val tags: List<String>? = emptyList(),
    @SerialName("reactions") val reactions: Int? = null,
    @SerialName("comments") val comments: Int? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("source") val source: String,
    @SerialName("original_url") val originalUrl: String? = null,
    @SerialName("comments_url") val commentsUrl: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("subreddit") val subreddit: String? = null,
    @SerialName("flair_text") val flairText: String? = null,
    @SerialName("flair_background_color") val flairBackgroundColor: String? = null,
    @SerialName("flair_text_color") val flairTextColor: String? = null,
)