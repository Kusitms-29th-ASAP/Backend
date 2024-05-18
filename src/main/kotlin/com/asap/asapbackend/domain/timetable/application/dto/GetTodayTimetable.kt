package com.asap.asapbackend.domain.timetable.application.dto

class GetTodayTimetable {
    data class Response(
        val timetables: List<Timetable>
    )
    data class Timetable(
        val time: Int,
        val subject: String
    )
}