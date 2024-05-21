package com.asap.asapbackend.domain.classroom.presentation

import com.asap.asapbackend.domain.classroom.application.ClassroomService
import com.asap.asapbackend.domain.classroom.application.dto.*
import org.springframework.web.bind.annotation.*

@RestController
class ClassroomController(
    private val classroomService: ClassroomService
) {
    @PostMapping(ClassroomApi.V1.ANNOUNCEMENT)
    fun addClassroomAnnouncement(
        @RequestBody request: CreateClassroomAnnouncement.Request
    ) {
        classroomService.createClassroomAnnouncement(request)
    }

    @GetMapping(ClassroomApi.V1.TODAY_ANNOUNCEMENT)
    fun getTodayClassroomAnnouncement(): GetTodayClassroomAnnouncement.Response {
        return classroomService.getTodayClassroomAnnouncement()
    }

    @GetMapping(ClassroomApi.V1.ANNOUNCEMENT)
    fun getClassroomAnnouncements(): GetClassroomAnnouncements.Response {
        return classroomService.getClassroomAnnouncements()
    }

    @GetMapping(ClassroomApi.V1.ANNOUNCEMENT_DETAIL)
    fun getAnnouncementDetail(@PathVariable classroomAnnouncementId:Long) : GetClassroomAnnouncementDetail.Response {
        return classroomService.getClassroomAnnouncementDetail(classroomAnnouncementId)
    }

    @GetMapping(ClassroomApi.V1.SCHOOL_CLASSROOM)
    fun getSchoolClassrooms(@PathVariable schoolId: Long): GetSchoolClassroom.Response {
        return classroomService.getSchoolClassrooms(GetSchoolClassroom.Request(schoolId))
    }
}