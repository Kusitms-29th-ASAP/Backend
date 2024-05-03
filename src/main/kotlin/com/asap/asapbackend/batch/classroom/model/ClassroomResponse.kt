package com.asap.asapbackend.batch.classroom.model

import com.asap.asapbackend.domain.school.domain.model.School

data class ClassroomResponse(
        val grade: Int,
        val classNumber: String,
        val school: School
)
