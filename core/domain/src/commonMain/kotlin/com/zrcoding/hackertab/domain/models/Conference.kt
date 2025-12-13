package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class Conference(
    override val id: String,
    override val url: String,
    override val title: String,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val tag: String,
    val online: Boolean,
    val city: String?,
    val country: String?,
): BaseArticle()
