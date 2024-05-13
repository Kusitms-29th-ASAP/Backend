package com.asap.asapbackend.domain.timetable.application.dto

class GetTodayTimetable {
    data class Response(
        val time: Int?,
        val subject: String?
    )
}