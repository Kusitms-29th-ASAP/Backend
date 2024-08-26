package com.asap.asapbackend.domain.timetable.application.dto

import java.time.DayOfWeek

class GetThisWeekTimetable {
    data class Response(
        val timetables : Map<DayOfWeek,List<Timetable>>
    )
    data class Timetable(
        val time: Int,
        val subject: String
    )
}