package com.asap.asapbackend.domain.announcement.application

import com.asap.asapbackend.domain.announcement.application.dto.GetEducationOfficeAnnouncementDetail
import com.asap.asapbackend.domain.announcement.application.dto.GetEducationOfficeAnnouncementSlice
import com.asap.asapbackend.domain.announcement.application.dto.GetSchoolAnnouncementDetail
import com.asap.asapbackend.domain.announcement.application.dto.GetSchoolAnnouncementSlice
import com.asap.asapbackend.domain.announcement.domain.service.SchoolAnnouncementReader
import com.asap.asapbackend.domain.child.domain.service.ChildReader
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomReader
import com.asap.asapbackend.global.security.getCurrentUserId
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SchoolAnnouncementService(
    private val schoolAnnouncementReader: SchoolAnnouncementReader,
    private val classroomReader: ClassroomReader,
    private val childReader: ChildReader
) {

    fun getSchoolAnnouncementSlice(request: GetSchoolAnnouncementSlice.Request): GetSchoolAnnouncementSlice.Response {
        val classroom = getChildClassroom()
        val schoolAnnouncements = schoolAnnouncementReader.findAllSchoolAnnouncements(
            classroom.id,
            PageRequest.of(request.page, request.size)
        )
        return GetSchoolAnnouncementSlice.convertSchoolAnnouncementToResponse(schoolAnnouncements){
            schoolAnnouncementReader.findSchoolAnnouncementCategory(it, classroom.id)
        }
    }

    fun getSchoolAnnouncementDetail(request: GetSchoolAnnouncementDetail.Request) : GetSchoolAnnouncementDetail.Response {
        val classroom = getChildClassroom()
        val schoolAnnouncement = schoolAnnouncementReader.findSchoolAnnouncement(request.announcementId)
        return GetSchoolAnnouncementDetail.convertSchoolAnnouncementToResponse(schoolAnnouncement){
            schoolAnnouncementReader.findSchoolAnnouncementCategory(it, classroom.id)
        }
    }

    fun getEducationOfficeAnnouncementSlice(request: GetEducationOfficeAnnouncementSlice.Request): GetEducationOfficeAnnouncementSlice.Response {
        val classroom = getChildClassroom()
        val educationOfficeAnnouncements =
            schoolAnnouncementReader.findAllEducationOfficeAnnouncements(PageRequest.of(request.page, request.size))
        return GetEducationOfficeAnnouncementSlice.convertEducationOfficeAnnouncementToResponse(educationOfficeAnnouncements){
            schoolAnnouncementReader.findEducationOfficeAnnouncementCategory(it, classroom.id)
        }
    }

    fun getEducationOfficeAnnouncementDetail(request: GetEducationOfficeAnnouncementDetail.Request) : GetEducationOfficeAnnouncementDetail.Response {
        val classroom = getChildClassroom()
        val educationOfficeAnnouncement = schoolAnnouncementReader.findEducationOfficeAnnouncement(request.announcementId)
        return GetEducationOfficeAnnouncementDetail.convertEducationOfficeAnnouncementToResponse(educationOfficeAnnouncement){
            schoolAnnouncementReader.findEducationOfficeAnnouncementCategory(it, classroom.id)
        }
    }

    private fun getChildClassroom(): Classroom {
        val userId = getCurrentUserId()
        val child = childReader.findPrimaryChild(userId)
        val classroom = classroomReader.findByStudent(child.id)
        return classroom
    }

}