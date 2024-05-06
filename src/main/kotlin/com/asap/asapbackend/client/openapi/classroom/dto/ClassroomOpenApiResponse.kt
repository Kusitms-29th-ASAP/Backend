package com.asap.asapbackend.client.openapi.classroom.dto

import com.asap.asapbackend.batch.classroom.ClassroomInfoProvider
import com.asap.asapbackend.domain.school.domain.model.School

data class ClassroomOpenApiResponse(
    val classInfo: List<ClassInfo>?,
    val RESULT: Result?
)

data class ClassInfo(
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
    val ATPT_OFCDC_SC_CODE: String?,
    val ATPT_OFCDC_SC_NM: String?,
    val SD_SCHUL_CODE: String?,
    val SCHUL_NM: String?,
    val AY: String?,
    val GRADE: Int,
    val DGHT_CRSE_SC_NM: String?,
    val SCHUL_CRSE_SC_NM: String?,
    val ORD_SC_NM: String?,
    val DDDEP_NM: String?,
    val CLASS_NM: String,
    val LOAD_DTM: String?
) {
    fun toClassroomInfo(school: School): ClassroomInfoProvider.ClassroomInfo {
        return ClassroomInfoProvider.ClassroomInfo(
            school,
            GRADE,
            CLASS_NM
        )
    }
}