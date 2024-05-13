package com.asap.asapbackend.domain.timetable.domain.service

import com.asap.asapbackend.domain.timetable.application.dto.ReadThisWeekTimetable
import com.asap.asapbackend.domain.timetable.application.dto.toPeriod
import com.asap.asapbackend.domain.timetable.domain.model.Timetable
import com.asap.asapbackend.domain.timetable.domain.repository.TimetableRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate

@Service
class TimetableReader(
    private val timetableRepository: TimetableRepository
) {
    fun findTodayTimetableByClassroomId(classroomId: Long): List<Timetable?> {
        return timetableRepository.findBySubjectClassroomIdAndDayOrderByTime(classroomId, LocalDate.now())
    }

    fun findThisWeekTimetableByClassroomId(classroomId: Long): ReadThisWeekTimetable.Response {
        val monday = timetableRepository.findBySubjectClassroomIdAndDayOrderByTime(
            classroomId, LocalDate.now().with(DayOfWeek.MONDAY)
        ).toPeriod()
        val tuesday = timetableRepository.findBySubjectClassroomIdAndDayOrderByTime(
            classroomId, LocalDate.now().with(DayOfWeek.TUESDAY)
        ).toPeriod()
        val wednesday = timetableRepository.findBySubjectClassroomIdAndDayOrderByTime(
            classroomId, LocalDate.now().with(DayOfWeek.WEDNESDAY)
        ).toPeriod()
        val thursday = timetableRepository.findBySubjectClassroomIdAndDayOrderByTime(
            classroomId, LocalDate.now().with(DayOfWeek.THURSDAY)
        ).toPeriod()
        val friday = timetableRepository.findBySubjectClassroomIdAndDayOrderByTime(
            classroomId, LocalDate.now().with(DayOfWeek.FRIDAY)
        ).toPeriod()
        return ReadThisWeekTimetable.Response(
            monday = monday,
            tuesday = tuesday,
            wednesday = wednesday,
            thursday = thursday,
            friday = friday
        )
    }
}