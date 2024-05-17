package com.asap.asapbackend.domain.timetable.application

import com.asap.asapbackend.domain.child.domain.service.ChildReader
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomReader
import com.asap.asapbackend.domain.timetable.application.dto.GetThisWeekTimetable
import com.asap.asapbackend.domain.timetable.application.dto.GetTodayTimetable
import com.asap.asapbackend.domain.timetable.domain.service.TimetableReader
import com.asap.asapbackend.global.security.getCurrentUserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TimetableService(
    private val classroomReader: ClassroomReader,
    private val childReader: ChildReader,
    private val timetableReader: TimetableReader
) {
    fun getTodayTimetable(): GetTodayTimetable.Response {
        val userId = getCurrentUserId()
        val studentId = childReader.findPrimaryChild(userId).id
        val classroomId = classroomReader.findByStudent(studentId).id
        val todayTimetables = timetableReader.findTodayTimetableByClassroomId(classroomId)
        val timetables = todayTimetables.map {
            GetTodayTimetable.Timetable(it.time, it.subject.name)
        }
        return GetTodayTimetable.Response(timetables)
    }

    fun getThisWeekTimetable(): GetThisWeekTimetable.Response {
        val userId = getCurrentUserId()
        val studentId = childReader.findPrimaryChild(userId).id
        val classroomId = classroomReader.findByStudent(studentId).id
        val weekTimetables = timetableReader.findThisWeekTimetableByClassroomId(classroomId)
        val weekDataList = weekTimetables.mapValues { (_, timetables) ->
            timetables.map { timetable ->
                GetThisWeekTimetable.Timetable(timetable?.time, timetable?.subject?.name)
            }
        }
        return GetThisWeekTimetable.Response(weekDataList)
    }
}