package com.asap.asapbackend.domain.classroom.presentation

import com.asap.asapbackend.domain.classroom.application.ClassroomService
import com.asap.asapbackend.domain.classroom.application.dto.CreateAnnouncement
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
}