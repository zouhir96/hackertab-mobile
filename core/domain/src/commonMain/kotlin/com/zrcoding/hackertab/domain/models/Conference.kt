package com.zrcoding.hackertab.domain.models

import kotlinx.datetime.LocalDateTime

data class Conference(
    override val id: String,
    val url: String,
    val title: String,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val tag: String,
    val online: Boolean,
    val city: String?,
    val country: String?,
): BaseModel()
