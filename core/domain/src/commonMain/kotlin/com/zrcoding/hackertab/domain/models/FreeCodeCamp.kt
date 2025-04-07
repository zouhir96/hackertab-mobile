package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class FreeCodeCamp(
    override val id: String,
    val title: String,
    val url: String,
    val isoDate: LocalDateTime,
    val categories: List<String>,
): BaseModel()