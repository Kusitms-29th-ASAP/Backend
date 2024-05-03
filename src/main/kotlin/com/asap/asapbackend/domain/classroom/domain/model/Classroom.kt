package com.asap.asapbackend.domain.classroom.domain.model

import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.*

@Entity
class Classroom(
    grade: Int,
    val classNumber: String,
    @Embedded
    val year: Year,
    @ManyToOne(fetch = FetchType.LAZY)
    val school: School
) : BaseDateEntity() {
    @Column(
        nullable = false,
        columnDefinition = "varchar(255)"
    )
    @Enumerated(EnumType.STRING)
    val grade: Grade = Grade.convert(grade)
}