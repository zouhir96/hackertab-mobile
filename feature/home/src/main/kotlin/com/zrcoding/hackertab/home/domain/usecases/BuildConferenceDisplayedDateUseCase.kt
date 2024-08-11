package com.zrcoding.hackertab.home.domain.usecases

import com.zrcoding.hackertab.home.domain.models.Conference
import kotlinx.datetime.LocalDateTime
import java.util.Locale

object BuildConferenceDisplayedDateUseCase {
    operator fun invoke(conf: Conference): String {
        return with(conf) {
            when {
                startDate == null -> ""
                endDate == null || startDate == endDate -> toMonthWithDay(startDate)
                else -> {
                    val start = toMonthWithDay(startDate)
                    if (startDate.month != endDate.month) {
                        "$start - ${toMonthWithDay(endDate)}"
                    } else {
                        "$start - ${endDate.dayOfMonth}"
                    }
                }
            }
        }
    }

    private fun toMonthWithDay(date: LocalDateTime): String {
        val month = date.month.name.lowercase().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
        return "$month ${date.dayOfMonth}"
    }
}