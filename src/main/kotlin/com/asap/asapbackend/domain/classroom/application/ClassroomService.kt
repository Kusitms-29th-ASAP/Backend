package com.asap.asapbackend.domain.classroom.application

import com.asap.asapbackend.domain.child.domain.service.ChildReader
import com.asap.asapbackend.domain.classroom.application.dto.CreateAnnouncement
import com.asap.asapbackend.domain.classroom.application.dto.GetAnnouncements
import com.asap.asapbackend.domain.classroom.application.dto.GetTodayAnnouncement
import com.asap.asapbackend.domain.classroom.domain.service.AnnouncementReader
import com.asap.asapbackend.domain.classroom.domain.service.ClassModifier
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomReader
import com.asap.asapbackend.domain.teacher.domain.service.TeacherReader
import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.domain.todo.domain.service.TodoAppender
import com.asap.asapbackend.global.security.getTeacherId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ClassroomService(
    private val classroomReader: ClassroomReader,
    private val classModifier: ClassModifier,
    private val teacherReader: TeacherReader,
    private val childReader: ChildReader,
    private val todoAppender: TodoAppender,
    private val announcementReader: AnnouncementReader
) {

    @Transactional
    fun createAnnouncement(request: CreateAnnouncement.Request) {
        val teacherId = getTeacherId()
        val classroom = classroomReader.findByTeacher(teacherId)
        val teacher = teacherReader.findById(teacherId)

        classroom.addAnnouncement(teacher, request.toAnnouncementDescription(), request.writeDate)
        classModifier.update(classroom)

        val studentIds = classroom.getStudentIds()
        val students = childReader.findAllByIds(studentIds)
        val todos = request.announcementDetails.filter {
            it.isLinkedWithTodo
        }.flatMap {  // 30*4 = 120
            students.map { student ->
                Todo(
                    child = student,
                    description = it.description,
                    type = it.todoType,
                    deadline = it.deadline
                )
            }
        }.toSet()
        todoAppender.appendAllBatch(todos)
    }

    fun getTodayAnnouncement(): GetTodayAnnouncement.Response {
        val teacherId = getTeacherId()
        val descriptions =
            announcementReader.getRecentAnnouncementByTeacherIdOrNull(teacherId)?.descriptions ?: emptyList()
        return GetTodayAnnouncement.Response(descriptions)
    }

    fun getAnnouncements(): GetAnnouncements.Response {
        val teacherId = getTeacherId()
        val teacher = teacherReader.findById(teacherId).name
        val announcementDataList = announcementReader.getAllByTeacherId(teacherId)
        val announcements = GetAnnouncements().toAnnouncementInfo(announcementDataList)
        return GetAnnouncements.Response(teacher, announcements)
    }
}
