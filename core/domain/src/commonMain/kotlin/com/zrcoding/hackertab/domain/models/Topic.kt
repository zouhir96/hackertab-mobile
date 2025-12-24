package com.zrcoding.hackertab.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    val value: String,
    val label: String,
    val category: String? = null
) {
    companion object {
        val global = Topic(
            value = "global",
            label = "Trending",
            category = null
        )
    }
}