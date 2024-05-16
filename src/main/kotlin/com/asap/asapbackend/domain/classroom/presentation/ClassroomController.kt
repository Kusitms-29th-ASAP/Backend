package com.asap.asapbackend.domain.classroom.presentation

import com.asap.asapbackend.domain.classroom.application.ClassroomService
import com.asap.asapbackend.domain.classroom.application.dto.CreateAnnouncement
import com.asap.asapbackend.domain.classroom.application.dto.GetAnnouncements
import com.asap.asapbackend.domain.classroom.application.dto.GetTodayAnnouncement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ClassroomController(
    private val classroomService: ClassroomService
) {


    @PostMapping(ClassroomApi.V1.ANNOUNCEMENT)
    fun addAnnouncement(
        @RequestBody request: CreateAnnouncement.Request
    ) {
        classroomService.createAnnouncement(request)
    }

    @GetMapping(ClassroomApi.V1.TODAY_ANNOUNCEMENT)
    fun getTodayAnnouncement(): GetTodayAnnouncement.Response {
        return classroomService.getTodayAnnouncement()
    }

    @GetMapping(ClassroomApi.V1.ANNOUNCEMENT)
    fun getAnnouncements(): GetAnnouncements.Response {
        return classroomService.getAnnouncements()
    }
}