package com.asap.asapbackend.domain.classroom.domain.model

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity

@Entity
class Classroom(
    val garde: Grade,
    val classNumber: Int,
    @Embedded
    val year: Year
): BaseDateEntity()