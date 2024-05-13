package com.asap.asapbackend.domain.timetable.domain.service

import com.asap.asapbackend.domain.timetable.domain.model.Timetable
import com.asap.asapbackend.domain.timetable.domain.repository.TimetableRepository
import org.springframework.stereotype.Service

@Service
class TimetableReader(
    private val timetableRepository: TimetableRepository
) {
    fun findTodayTimetableByClassroomId(classroomId: Long): List<Timetable?> {
        return timetableRepository.findBySubjectClassroomId(classroomId)
    }
}