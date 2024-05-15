package com.asap.asapbackend.domain.timetable.domain.service

import com.asap.asapbackend.domain.timetable.application.dto.GetThisWeekTimetable
import com.asap.asapbackend.domain.timetable.domain.model.Timetable
import com.asap.asapbackend.domain.timetable.domain.repository.TimetableRepository
import org.springframework.stereotype.Service
import java.sql.Time
import java.time.DayOfWeek
import java.time.LocalDate

@Service
class TimetableReader(
    private val timetableRepository: TimetableRepository
) {
    fun findTodayTimetableByClassroomId(classroomId: Long): List<Timetable?> {
        return timetableRepository.findBySubjectClassroomIdAndDayOrderByTime(classroomId, LocalDate.now())
    }

    fun findThisWeekTimetableByClassroomId(classroomId: Long): List<List<Timetable?>> {
        val daysOfWeek = listOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
        )
        return daysOfWeek.map { day ->
            timetableRepository.findBySubjectClassroomIdAndDayOrderByTime(classroomId, LocalDate.now().with(day))
        }
    }
}