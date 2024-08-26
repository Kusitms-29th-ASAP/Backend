package com.asap.asapbackend.domain.timetable.domain.model

import java.time.LocalDateTime

class Subject(
    id: Long = 0,
    classroomId: Long,
    name: String,
    semester: String,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now()
) {
    val id: Long = id

    val name : String = name

    val semester : String = semester

    val classroomId: Long = classroomId

    val createdAt: LocalDateTime = createdAt
    val updatedAt: LocalDateTime = updatedAt


    fun isSameSubject(
        name: String,
        semester: String,
        classroomId: Long
    ): Boolean {
        return this.name == name && this.semester == semester && this.classroomId == classroomId
    }
}