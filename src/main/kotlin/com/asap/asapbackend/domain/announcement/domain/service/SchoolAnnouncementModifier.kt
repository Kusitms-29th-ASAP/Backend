package com.asap.asapbackend.domain.announcement.domain.service

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncementCategory
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncementCategory
import com.asap.asapbackend.domain.announcement.domain.repository.EducationOfficeAnnouncementCategoryRepository
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementCategoryRepository
import com.asap.asapbackend.domain.announcement.domain.vo.AnnouncementCategory
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import org.springframework.stereotype.Service

@Service
class SchoolAnnouncementModifier(
    private val schoolAnnouncementCategoryRepository: SchoolAnnouncementCategoryRepository,
    private val educationOfficeAnnouncementCategoryRepository: EducationOfficeAnnouncementCategoryRepository
) {

    fun upsertSchoolAnnouncementCategory(
        announcementCategory: AnnouncementCategory,
        classroom: Classroom,
        schoolAnnouncement: SchoolAnnouncement
    ) {
        schoolAnnouncementCategoryRepository.findBySchoolAnnouncementIdAndClassroomId(
            schoolAnnouncement.id,
            classroom.id
        )
            ?.let {
                it.updateCategory(announcementCategory)
                schoolAnnouncementCategoryRepository.save(it)
            } ?: schoolAnnouncementCategoryRepository.save(
            SchoolAnnouncementCategory(
                classroom = classroom,
                schoolAnnouncement = schoolAnnouncement,
                announcementCategory = announcementCategory
            )
        )
    }

    fun upsertEducationOfficeAnnouncementCategory(
        announcementCategory: AnnouncementCategory,
        classroom: Classroom,
        educationOfficeAnnouncement: EducationOfficeAnnouncement
    ) {
        educationOfficeAnnouncementCategoryRepository.findByEducationOfficeAnnouncementIdAndClassroomId(
            educationOfficeAnnouncement.id,
            classroom.id
        )?.let {
            it.updateCategory(announcementCategory)
            educationOfficeAnnouncementCategoryRepository.save(it)
        } ?: educationOfficeAnnouncementCategoryRepository.save(
            EducationOfficeAnnouncementCategory(
                classroom = classroom,
                educationOfficeAnnouncement = educationOfficeAnnouncement,
                announcementCategory = announcementCategory
            )
        )
    }
}