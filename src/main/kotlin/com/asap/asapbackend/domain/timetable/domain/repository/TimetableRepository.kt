package com.asap.asapbackend.domain.timetable.domain.repository

import com.asap.asapbackend.domain.timetable.domain.model.Timetable
import org.springframework.data.jpa.repository.JpaRepository

interface TimetableRepository : JpaRepository<Timetable, Long> {
}