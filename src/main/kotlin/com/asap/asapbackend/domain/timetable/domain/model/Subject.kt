package com.asap.asapbackend.domain.timetable.domain.model

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class Subject(
    classroom: Classroom,
    name: String?,
    semester: String
) : BaseDateEntity() {

    val name : String? = name

    val semester : String = semester

    @ManyToOne(fetch = FetchType.LAZY)
    val classroom: Classroom = classroom

}