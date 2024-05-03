package com.asap.asapbackend.domain.timetable.application

import com.asap.asapbackend.domain.timetable.domain.model.TimetableResponse
import com.asap.asapbackend.domain.timetable.domain.service.TimetableReader
import org.springframework.stereotype.Service

@Service
class TimetableService(
        private val timetableReader: TimetableReader
) {
    fun getTimetable(): List<TimetableResponse> {
        return timetableReader.getTimetable()
    }
}