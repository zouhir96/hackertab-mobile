package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDate

data class Conference(
    override val id: String,
    override val url: String,
    override val title: String,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val tags: List<String>,
    val online: Boolean,
    val city: String?,
    val country: String?,
): BaseArticle()
