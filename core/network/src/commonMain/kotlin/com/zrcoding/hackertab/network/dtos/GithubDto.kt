package com.zrcoding.hackertab.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubDto(
    @SerialName("id") val id: String,
    @SerialName("url") val url: String,
    @SerialName("title") val title: String,
    @SerialName("programmingLanguage") val programmingLanguage: String,
    @SerialName("stars") val stars: String,
    @SerialName("source") val source: String,
    @SerialName("description") val description: String,
    @SerialName("owner") val owner: String,
    @SerialName("forks") val forks: String,
    @SerialName("starsInDateRange") val starsInDateRange: String,
    @SerialName("name") val name: String,
)