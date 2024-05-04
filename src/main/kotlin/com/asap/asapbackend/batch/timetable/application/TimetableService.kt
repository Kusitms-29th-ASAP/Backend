package com.asap.asapbackend.batch.timetable.application

import com.asap.asapbackend.batch.timetable.model.TimetableResponse
import com.asap.asapbackend.batch.timetable.service.TimetableReader
import org.springframework.stereotype.Service

@Service
class TimetableService(
        private val timetableReader: TimetableReader
) {
    fun getTimetable(): List<TimetableResponse> {
        return timetableReader.getTimetable()
    }
}