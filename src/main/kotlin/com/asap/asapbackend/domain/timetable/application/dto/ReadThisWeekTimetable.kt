package com.asap.asapbackend.domain.timetable.application.dto

import com.asap.asapbackend.domain.timetable.domain.model.Timetable

class ReadThisWeekTimetable {
    data class Response(
        val monday: List<Period?>,
        val tuesday: List<Period?>,
        val wednesday: List<Period?>,
        val thursday: List<Period?>,
        val friday: List<Period?>
    )
    data class Period(
        val time: Int?,
        val subject: String?
    )
    fun toPeriod(timetables: List<Timetable?>): List<Period> {
        return timetables.map {
            Period(
                time = it?.time,
                subject = it?.subject?.name
            )
        }
    }
}