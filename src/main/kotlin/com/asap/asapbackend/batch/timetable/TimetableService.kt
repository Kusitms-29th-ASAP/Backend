package com.asap.asapbackend.batch.timetable

import com.asap.asapbackend.batch.timetable.dto.TimetableResponse
import org.springframework.stereotype.Service

@Service
class TimetableService(
        private val timetableOpenApiClient: TimetableOpenApiClient
) {
    fun getTimetable(): List<TimetableResponse> {
        return timetableOpenApiClient.getTimetable()
    }
}