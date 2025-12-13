package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class FreeCodeCamp(
    override val id: String,
    override val title: String,
    override val url: String,
    val isoDate: LocalDateTime,
    val categories: List<String>,
): BaseArticle()