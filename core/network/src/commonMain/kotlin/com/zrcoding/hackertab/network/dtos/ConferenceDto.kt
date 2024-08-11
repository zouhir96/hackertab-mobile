package com.zrcoding.hackertab.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConferenceDto(
    @SerialName("id") val id: String,
    @SerialName("url") val url: String,
    @SerialName("title") val title: String,
    @SerialName("start_date") val startDate: Long? = null,
    @SerialName("end_date") val endDate: Long? = null,
    @SerialName("tag") val tag: String,
    @SerialName("online") val online: Boolean,
    @SerialName("city") val city: String? = null,
    @SerialName("country") val country: String? = null,
)
