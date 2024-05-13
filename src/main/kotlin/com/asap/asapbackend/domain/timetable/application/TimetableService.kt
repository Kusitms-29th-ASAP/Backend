package com.asap.asapbackend.domain.timetable.application

import com.asap.asapbackend.domain.child.domain.service.ChildReader
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomReader
import com.asap.asapbackend.domain.timetable.application.dto.GetTodayTimetable
import com.asap.asapbackend.domain.timetable.domain.service.TimetableReader
import com.asap.asapbackend.global.security.getCurrentUserId
import org.springframework.stereotype.Service

@Service
class TimetableService(
    private val classroomReader: ClassroomReader,
    private val childReader: ChildReader,
    private val timetableReader: TimetableReader
) {
    fun getTodayTimetable(): List<GetTodayTimetable.Response> {
        val userId = getCurrentUserId()
        val studentId = childReader.findByParentId(userId).id
        val classroomId = classroomReader.findByStudent(studentId).id
        val todayTimetables = timetableReader.findTodayTimetableByClassroomId(classroomId)
        return todayTimetables.map {
            GetTodayTimetable.Response(it?.time,it?.subject?.name)
        }
    }
}