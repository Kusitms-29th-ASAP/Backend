package com.asap.asapbackend.domain.timetable.presentation

object TimetableApi {
    object V1{
        const val BASE_URL = "/api/v1/timetables"
        const val TODAY = "$BASE_URL/today"
        const val WEEK = "$BASE_URL/week"
    }
}