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
    val GRADE: Int,
    val CLASS_NM: String
) {
    fun toClassroomInfo(school: School): ClassroomInfoProvider.ClassroomInfo {
        return ClassroomInfoProvider.ClassroomInfo(
            school,
            GRADE,
            CLASS_NM
        )
    }
}