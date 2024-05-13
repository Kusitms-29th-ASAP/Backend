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
    companion object {
        fun fromTimetable(timetable: Timetable?): Period {
            return Period(
                time = timetable?.time,
                subject = timetable?.subject?.name
            )
        }
    }
}
fun List<Timetable?>.toPeriod(): List<ReadThisWeekTimetable.Period> {
    return this.map { ReadThisWeekTimetable.fromTimetable(it) }
}