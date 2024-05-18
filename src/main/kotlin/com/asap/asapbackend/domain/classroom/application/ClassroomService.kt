package com.asap.asapbackend.domain.classroom.application

import com.asap.asapbackend.domain.child.domain.service.ChildReader
import com.asap.asapbackend.domain.classroom.application.dto.CreateAnnouncement
import com.asap.asapbackend.domain.classroom.application.dto.GetAnnouncementDetail
import com.asap.asapbackend.domain.classroom.application.dto.GetAnnouncements
import com.asap.asapbackend.domain.classroom.application.dto.GetTodayAnnouncement
import com.asap.asapbackend.domain.classroom.domain.service.AnnouncementReader
import com.asap.asapbackend.domain.classroom.domain.service.ClassModifier
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomReader
import com.asap.asapbackend.domain.teacher.domain.service.TeacherReader
import com.asap.asapbackend.domain.todo.domain.service.TodoAppender
import com.asap.asapbackend.global.security.getCurrentUserId
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

        classroom.addAnnouncement(teacher, request.toAnnouncementDescription())
        classModifier.update(classroom)

        val studentIds = classroom.getStudentIds()
        val students = childReader.findAllByIds(studentIds)
        val todos = request.toTodo(students)
        todoAppender.appendAllBatch(todos)
    }

    fun getTodayAnnouncement(): GetTodayAnnouncement.Response {
        val userId = getCurrentUserId()
        val childId = childReader.findPrimaryChild(userId).id
        val classroomId = classroomReader.findByStudent(childId).id
        return GetTodayAnnouncement.convertClassAnnouncementToResponse{
            announcementReader.findRecentAnnouncementByClassroomIdOrNull(classroomId)
        }
    }

    fun getAnnouncements(): GetAnnouncements.Response {
        val userId = getCurrentUserId()
        val childId = childReader.findPrimaryChild(userId).id
        val classroomId = classroomReader.findByStudent(childId).id
        val teacher = teacherReader.findByClassroomId(classroomId).name
        val announcementDataList = announcementReader.findAllByClassroomId(classroomId)
        val announcements = GetAnnouncements().toAnnouncementInfo(announcementDataList)
        return GetAnnouncements.Response(teacher, announcements)
    }

    fun getAnnouncementDetail(announcementId: Long): GetAnnouncementDetail.Response {
        val userId = getCurrentUserId()
        val childId = childReader.findPrimaryChild(userId).id
        val classroomId = classroomReader.findByStudent(childId).id
        val teacherName = teacherReader.findByClassroomId(classroomId).name
        val announcement = announcementReader.findById(announcementId)
        return GetAnnouncementDetail.Response(teacherName, announcement.getWriteDate(), announcement.descriptions)
    }
}
