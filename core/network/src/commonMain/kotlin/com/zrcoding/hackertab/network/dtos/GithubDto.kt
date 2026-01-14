package com.zrcoding.hackertab.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("owner") val owner: String,
    @SerialName("url") val url: String,
    @SerialName("description") val description: String,
    @SerialName("stars_count") val stars: Int,
    @SerialName("stars_in_range") val starsInDateRange: Long,
    @SerialName("forks_count") val forks: Int,
    @SerialName("technology") val programmingLanguage: String,
)