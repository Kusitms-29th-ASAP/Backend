package com.asap.asapbackend.domain.timetable.domain.repository

import com.asap.asapbackend.domain.timetable.domain.model.Timetable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface TimetableRepository : JpaRepository<Timetable, Long> {
    fun findBySubjectClassroomIdAndDayOrderByTime(classroomId: Long, day: LocalDate) : List<Timetable?>
}