package com.asap.asapbackend.batch.timetable

import com.asap.asapbackend.domain.school.domain.model.School
import java.time.LocalDate

interface TimetableInfoProvider {
    fun retrieveTimetableInfo(batchSize: Int, pageNumber: Int, day: LocalDate): TimetableDataContainer

    data class TimetableDataContainer(
        val timetableInfo: List<TimetableResponse>,
        val hasNext: Boolean
    )

    data class TimetableResponse(
        val school: School,
        val grade: Int,
        val className: String,
        val name: String?,
        val semester: String,
        val day: LocalDate,
        val time: Int
    )
}
