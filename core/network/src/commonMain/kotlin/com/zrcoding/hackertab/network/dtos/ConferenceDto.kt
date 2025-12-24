package com.zrcoding.hackertab.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConferenceDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("url") val url: String,
    @SerialName("start_date") val startDate: String?,
    @SerialName("end_date") val endDate: String?,
    @SerialName("online") val online: Boolean = false,
    @SerialName("city") val city: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("tags") val tags: List<String> = emptyList(),
)
