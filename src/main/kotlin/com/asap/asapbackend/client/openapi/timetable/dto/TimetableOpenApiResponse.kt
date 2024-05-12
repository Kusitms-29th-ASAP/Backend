package com.asap.asapbackend.client.openapi.timetable.dto

import com.asap.asapbackend.batch.timetable.TimetableInfoProvider
import com.asap.asapbackend.domain.school.domain.model.School
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class TimetableOpenApiResponse(
    val elsTimetable: List<TimetableInfo>?,
    val RESULT: Result?
)

data class TimetableInfo(
    val head: List<Head>?,
    val row: List<Row>?
)

data class Head(
    val list_total_count: Int?,
    val RESULT: Result?
)

data class Result(
    val CODE: String,
    val MESSAGE: String
)

data class Row(
    val ITRT_CNTNT: String?,
    val ALL_TI_YMD: String,
    val GRADE : Int,
    val CLASS_NM : String,
    val SEM: String,
    val PERIO: Int
) {

    fun toTimetableInfo(school: School): TimetableInfoProvider.TimetableResponse {
        return TimetableInfoProvider.TimetableResponse(
            school = school,
            grade = GRADE,
            className = CLASS_NM,
            name = ITRT_CNTNT,
            semester = SEM,
            day = LocalDate.parse(ALL_TI_YMD,DateTimeFormatter.BASIC_ISO_DATE),
            time = PERIO
        )
    }
}