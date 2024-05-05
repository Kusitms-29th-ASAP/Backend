package com.asap.asapbackend.domain.classroom.domain.model

import com.asap.asapbackend.domain.teacher.domain.model.Teacher
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class TeacherClassroom(
    teacher: Teacher,
    classroom: Classroom
) : BaseDateEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    val teacher = teacher

    @ManyToOne(fetch = FetchType.LAZY)
    val classroom = classroom
}