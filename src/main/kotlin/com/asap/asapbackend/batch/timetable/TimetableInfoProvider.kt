package com.asap.asapbackend.batch.timetable

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import java.time.LocalDate

interface TimetableInfoProvider {
    fun retrieveTimetableInfo(batchSize: Int, pageNumber: Int): TimetableDataContainer

    data class TimetableDataContainer(
        val timetableInfo: List<TimetableRequest>,
        val hasNext: Boolean
    )

    data class TimetableRequest(
        val classroom: Classroom,
        val name: String?,
        val semester: String,
        val day: LocalDate,
        val time: Int
    )
}
