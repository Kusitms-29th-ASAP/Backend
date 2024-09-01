package com.asap.asapbackend.domain.classroom.application

import com.asap.asapbackend.domain.child.domain.service.ChildReader
import com.asap.asapbackend.domain.classroom.application.dto.*
import com.asap.asapbackend.domain.classroom.domain.model.ClassroomAnnouncement
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomAnnouncementAppender
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomAnnouncementReader
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
    private val teacherReader: TeacherReader,
    private val childReader: ChildReader,
    private val todoAppender: TodoAppender,
    private val classroomAnnouncementReader: ClassroomAnnouncementReader,
    private val classroomAnnouncementAppender: ClassroomAnnouncementAppender,
) {

    @Transactional
    fun createClassroomAnnouncement(request: CreateClassroomAnnouncement.Request) {
        val teacherId = getTeacherId()
        val classroom = classroomReader.findByTeacher(teacherId)
        val teacher = teacherReader.findById(teacherId)

        classroomAnnouncementAppender.append(
            ClassroomAnnouncement(
                descriptions = request.toAnnouncementDescription(),
                classroomId = classroom.id,
                teacherId = teacher.id
            )
        )

        val studentIds = classroom.getStudentIds()
        val students = childReader.findAllByIds(studentIds)
        val todos = request.toTodo(students)
        todoAppender.appendAllBatch(todos)
    }

    fun getTodayClassroomAnnouncement(): GetTodayClassroomAnnouncement.Response {
        val userId = getCurrentUserId()
        val childId = childReader.findPrimaryChild(userId).id
        val classroomId = classroomReader.findByStudent(childId).id
        return GetTodayClassroomAnnouncement.convertClassAnnouncementToResponse {
            classroomAnnouncementReader.findRecentClassroomAnnouncementByClassroomIdOrNull(classroomId)
        }
    }

    fun getClassroomAnnouncements(): GetClassroomAnnouncements.Response {
        val userId = getCurrentUserId()
        val childId = childReader.findPrimaryChild(userId).id
        val classroomId = classroomReader.findByStudent(childId).id
        val teacher = teacherReader.findByClassroomId(classroomId).name
        val announcementDataList = classroomAnnouncementReader.findAllByClassroomId(classroomId)
        val announcements = GetClassroomAnnouncements().toAnnouncementInfo(announcementDataList)
        return GetClassroomAnnouncements.Response(teacher, announcements)
    }

    fun getClassroomAnnouncementDetail(classroomAnnouncementId: Long): GetClassroomAnnouncementDetail.Response {
        val userId = getCurrentUserId()
        val childId = childReader.findPrimaryChild(userId).id
        val classroomId = classroomReader.findByStudent(childId).id
        val teacherName = teacherReader.findByClassroomId(classroomId).name
        val announcement = classroomAnnouncementReader.findById(classroomAnnouncementId)
        return GetClassroomAnnouncementDetail.Response(
            teacherName,
            announcement.writeDate,
            announcement.descriptions
        )
    }

    fun getSchoolClassrooms(request: GetSchoolClassroom.Request): GetSchoolClassroom.Response {
        return GetSchoolClassroom.convertClassroomToResponse(classroomReader.findBySchoolId(request.schoolId))
    }
}
