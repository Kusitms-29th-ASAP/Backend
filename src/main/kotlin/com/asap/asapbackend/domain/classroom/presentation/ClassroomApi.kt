package com.asap.asapbackend.domain.classroom.presentation

object ClassroomApi {

    object V1{
        const val BASE_URL = "/api/v1/classrooms"

        const val ANNOUNCEMENT = "$BASE_URL/announcements"
        const val TODAY_ANNOUNCEMENT = "$ANNOUNCEMENT/today"
        const val ANNOUNCEMENT_DETAIL = "$ANNOUNCEMENT/detail"
    }
}