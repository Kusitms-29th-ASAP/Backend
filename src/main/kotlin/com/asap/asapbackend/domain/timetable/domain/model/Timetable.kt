package com.asap.asapbackend.domain.timetable.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

class Timetable(
    id: Long = 0,
    subject: Subject,
    day: LocalDate,
    time: Int,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now()
) {
    val id: Long = id

    val subject: Subject = subject

    val day: LocalDate = day

    val time: Int = time

    val createdAt: LocalDateTime = createdAt
    val updatedAt: LocalDateTime = updatedAt
}