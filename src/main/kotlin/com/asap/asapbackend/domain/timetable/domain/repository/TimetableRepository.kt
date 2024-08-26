package com.asap.asapbackend.domain.timetable.domain.repository

import com.asap.asapbackend.domain.timetable.domain.model.Timetable
import java.time.LocalDate

interface TimetableRepository{
    fun findByClassroomIdAndDayOrderByTime(classroomId: Long, day: LocalDate) : List<Timetable>

    fun insertBatch(timetables: Collection<Timetable>)
}