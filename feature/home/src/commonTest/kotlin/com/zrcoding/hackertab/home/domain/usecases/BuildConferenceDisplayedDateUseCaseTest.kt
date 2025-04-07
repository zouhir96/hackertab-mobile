package com.zrcoding.hackertab.home.domain.usecases

import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.usecases.BuildConferenceDisplayedDateUseCase
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class BuildConferenceDisplayedDateUseCaseTest {

    private val testConference = Conference(
        id = "ocurreret",
        url = "https://duckduckgo.com/?q=vulputate",
        title = "consetetur",
        startDate = null,
        endDate = null,
        tag = "fugit",
        online = false,
        city = null,
        country = null
    )

    @Test
    fun emptyStartDate() {
        // GIVEN
        val conf = testConference.copy(startDate = null)

        // WHEN
        val result = BuildConferenceDisplayedDateUseCase(conf)

        // THEN
        assertEquals(result, "")
    }

    @Test
    fun emptyEndDate() {
        // GIVEN
        val startDate = LocalDateTime(2024, 8,11,0,0)
        val conf = testConference.copy(startDate = startDate, endDate = null)

        // WHEN
        val result = BuildConferenceDisplayedDateUseCase(conf)

        // THEN
        assertEquals("August 11", result)
    }

    @Test
    fun sameDates() {
        // GIVEN
        val startDate =  LocalDateTime(2024, 8,11,0,0)
        val endDate =  LocalDateTime(2024, 8,11,0,0)
        val conf = testConference.copy(startDate = startDate, endDate = endDate)

        // WHEN
        val result = BuildConferenceDisplayedDateUseCase(conf)

        // THEN
        assertEquals("August 11", result)
    }

    @Test
    fun sameMonthWithDiffDays() {
        // GIVEN
        val startDate = LocalDateTime(2024, 4,20, 0,0)
        val endDate = LocalDateTime(2024, 4,22, 0,0)
        val conf = testConference.copy(startDate = startDate, endDate = endDate)

        // WHEN
        val result = BuildConferenceDisplayedDateUseCase(conf)

        // THEN
        assertEquals("April 20 - 22",result)
    }

    @Test
    fun diffMonthWithDiffDays() {
        // GIVEN
        val startDate = LocalDateTime(2024, 3,20, 0,0)
        val endDate = LocalDateTime(2024, 4,1, 0,0)
        val conf = testConference.copy(startDate = startDate, endDate = endDate)

        // WHEN
        val result = BuildConferenceDisplayedDateUseCase(conf)

        // THEN
        assertEquals("March 20 - April 1",result)
    }

    @Test
    fun diffYearsSameMonthWithDiffDays() {
        // GIVEN
        val startDate = LocalDateTime(2024, 4,20, 0,0)
        val endDate = LocalDateTime(2024, 4,22, 0,0)
        val conf = testConference.copy(startDate = startDate, endDate = endDate)

        // WHEN
        val result = BuildConferenceDisplayedDateUseCase(conf)

        // THEN
        assertEquals("April 20 - 22",result)
    }
}