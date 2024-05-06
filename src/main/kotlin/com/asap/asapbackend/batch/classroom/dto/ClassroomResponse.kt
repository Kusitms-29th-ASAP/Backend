package com.asap.asapbackend.batch.classroom.dto

import com.asap.asapbackend.domain.school.domain.model.School

data class ClassroomResponse(
        val grade: Int,
        val classNumber: String,
        val school: School
)
