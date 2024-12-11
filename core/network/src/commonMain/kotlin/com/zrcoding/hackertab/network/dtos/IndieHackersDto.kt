package com.zrcoding.hackertab.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IndieHackersDto(
    @SerialName("id") val id: String ? = null,
    @SerialName("title") val title: String,
    @SerialName("url") val url: String,
    @SerialName("published_at") val publishedAt: Long,
    @SerialName("tags") val tags: List<String>? = emptyList(),
    @SerialName("reactions") val reactions: String? = null,
    @SerialName("comments") val comments: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("source") val source: String,
)