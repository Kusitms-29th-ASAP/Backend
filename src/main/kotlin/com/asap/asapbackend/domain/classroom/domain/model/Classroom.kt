package com.asap.asapbackend.domain.classroom.domain.model

import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.*
import java.util.*

@Entity
class Classroom(
    grade: Int,
    className: String,
    school: School
) : BaseDateEntity() {
    @Column(
        nullable = false,
        columnDefinition = "varchar(255)"
    )
    @Enumerated(EnumType.STRING)
    val grade: Grade = Grade.convert(grade)

    val className: String = className

    @Embedded
    val year: Year = Year()

    val classCode: String = UUID.randomUUID().toString().split("-")[0]

    @ManyToOne(fetch = FetchType.LAZY)
    val school: School = school
}