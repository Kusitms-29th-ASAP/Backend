package com.asap.asapbackend.domain.timetable.domain.model

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class Timetable(
    subject: Subject,
    day: LocalDate,
    time: Int
) : BaseDateEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    val subject: Subject = subject

    val day: LocalDate = day

    val time: Int = time
}