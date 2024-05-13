package com.asap.asapbackend.domain.timetable.application

import com.asap.asapbackend.domain.child.domain.service.ChildReader
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomReader
import com.asap.asapbackend.domain.timetable.application.dto.ReadThisWeekTimetable
import com.asap.asapbackend.domain.timetable.application.dto.ReadTodayTimetable
import com.asap.asapbackend.domain.timetable.domain.service.TimetableReader
import com.asap.asapbackend.global.security.getCurrentUserId
import org.springframework.stereotype.Service

@Service
class TimetableService(
    private val classroomReader: ClassroomReader,
    private val childReader: ChildReader,
    private val timetableReader: TimetableReader
) {
    fun getTodayTimetable(): ReadTodayTimetable.Response {
        val userId = getCurrentUserId()
        val studentId = childReader.findPrimaryChildByParentId(userId).childId
        val classroomId = classroomReader.findByStudent(studentId).id
        val todayTimetables = timetableReader.findTodayTimetableByClassroomId(classroomId)
        val timetables = todayTimetables.map {
            ReadTodayTimetable.Timetable(it?.time,it?.subject?.name)
        }
        return ReadTodayTimetable.Response(timetables)
    }

    fun getThisWeekTimetable(): ReadThisWeekTimetable.Response {
        val userId = getCurrentUserId()
        val studentId = childReader.findPrimaryChildByParentId(userId).childId
        val classroomId = classroomReader.findByStudent(studentId).id
        return timetableReader.findThisWeekTimetableByClassroomId(classroomId)
    }
}