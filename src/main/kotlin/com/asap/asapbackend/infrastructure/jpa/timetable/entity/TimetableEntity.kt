package com.asap.asapbackend.infrastructure.jpa.timetable.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "timetable",
    indexes = [
        Index(name = "idx_timetable_subject_id", columnList = "subject_id"),
        Index(name="unique_timetable_day_time_subject_id", columnList = "day, time, subject_id", unique = true)
    ]
)
class TimetableEntity(
    id: Long,
    subjectId: Long,
    day: LocalDate,
    time: Int,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id

    @Column(name = "subject_id")
    val subjectId: Long = subjectId

    @JoinColumn(name = "subject_id", insertable = false, updatable = false, nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var subject: SubjectEntity

    val day: LocalDate = day
    val time: Int = time

    val createdAt: LocalDateTime = createdAt
    val updatedAt: LocalDateTime = updatedAt
}