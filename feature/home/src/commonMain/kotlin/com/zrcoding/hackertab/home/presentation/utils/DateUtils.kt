package com.zrcoding.hackertab.home.presentation.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.timeAgo(): String {
    val timeZone = TimeZone.currentSystemDefault()
    val currentTime = Clock.System.now().toLocalDateTime(timeZone)
    val duration = currentTime.toInstant(timeZone).minus(this.toInstant(timeZone))

    return when {
        duration.inWholeDays > 0 -> "${duration.inWholeDays} day${if (duration.inWholeDays > 1) "s" else ""} ago"
        duration.inWholeHours > 0 -> "${duration.inWholeHours} hour${if (duration.inWholeHours > 1) "s" else ""} ago"
        duration.inWholeMinutes > 0 -> "${duration.inWholeMinutes} minute${if (duration.inWholeMinutes > 1) "s" else ""} ago"
        duration.inWholeSeconds > 0 -> "${duration.inWholeSeconds} second${if (duration.inWholeSeconds > 1) "s" else ""} ago"
        else -> "just now"
    }
}