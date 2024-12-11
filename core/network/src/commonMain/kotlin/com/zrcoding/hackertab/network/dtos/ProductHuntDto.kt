package com.zrcoding.hackertab.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductHuntDto(
    @SerialName("id") val id: String,
    @SerialName("url") val url: String,
    @SerialName("title") val title: String,
    @SerialName("published_at") val publishedAt: Long?= null,
    @SerialName("tags") val tags: List<String>? = emptyList(),
    @SerialName("reactions") val reactions: Int? = null,
    @SerialName("comments") val comments: Int? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("source") val source: String,
    @SerialName("description") val description: String? = null,
)