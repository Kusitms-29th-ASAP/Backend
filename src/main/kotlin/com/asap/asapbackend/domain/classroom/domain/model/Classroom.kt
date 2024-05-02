package com.asap.asapbackend.domain.classroom.domain.model

import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class Classroom(
        val grade: Int,
        val classNumber: String,
        @Embedded
        val year: Year,
        @ManyToOne(fetch = FetchType.LAZY)
        val school: School
): BaseDateEntity()