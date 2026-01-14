package com.zrcoding.hackertab.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductHuntDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("url") val url: String,
    @SerialName("tagline") val description: String,
    @SerialName("votes_count") val reactions: Int,
    @SerialName("comments_count") val comments: Int,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("published_at") val publishedAt: String,
    @SerialName("topics") val tags: List<String>,
)