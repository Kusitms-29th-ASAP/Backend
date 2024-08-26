package com.asap.asapbackend.infrastructure.jpa.timetable.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "subject",
    indexes = [
        Index(name = "idx_subject_classroom_id", columnList = "classroom_id")
    ]
)
class SubjectEntity(
    id: Long,
    classroomId: Long,
    name: String,
    semester: String,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id

    val classroomId: Long = classroomId

    var name : String = name

    val semester : String = semester

    val createdAt: LocalDateTime = createdAt
    val updatedAt: LocalDateTime = updatedAt

    fun changeName(name: String?) {
        name?.let {
            this.name = it
        }
    }
}