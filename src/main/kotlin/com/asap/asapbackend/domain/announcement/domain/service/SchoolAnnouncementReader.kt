package com.asap.asapbackend.domain.announcement.domain.service

import com.asap.asapbackend.domain.announcement.domain.exception.AnnouncementException
import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.asap.asapbackend.domain.announcement.domain.repository.EducationOfficeAnnouncementCategoryRepository
import com.asap.asapbackend.domain.announcement.domain.repository.EducationOfficeAnnouncementRepository
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementCategoryRepository
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementRepository
import com.asap.asapbackend.domain.announcement.domain.vo.AnnouncementCategory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SchoolAnnouncementReader(
    private val schoolAnnouncementRepository: SchoolAnnouncementRepository,
    private val educationOfficeAnnouncementRepository: EducationOfficeAnnouncementRepository,
    private val schoolAnnouncementCategoryRepository: SchoolAnnouncementCategoryRepository,
    private val educationOfficeAnnouncementCategoryRepository: EducationOfficeAnnouncementCategoryRepository
) {

    fun getLastOfficeEducationAnnouncementId(): Int {
        return educationOfficeAnnouncementRepository.findLastIndex()
    }

    fun findAllSchoolAnnouncements(classId: Long, pageable: Pageable): Slice<SchoolAnnouncement> {
        return schoolAnnouncementRepository.findAllByClassroomId(classId, pageable)
    }

    fun findAllEducationOfficeAnnouncements(pageable: Pageable): Slice<EducationOfficeAnnouncement> {
        return educationOfficeAnnouncementRepository.findAll(
            PageRequest.of(
                pageable.pageNumber,
                pageable.pageSize,
                Sort.by(Sort.Direction.DESC, "id")
            )
        )
    }

    fun findSchoolAnnouncementCategory(schoolAnnouncementId: Long, classId: Long): AnnouncementCategory {
        return schoolAnnouncementCategoryRepository.findBySchoolAnnouncementIdAndClassroomId(
            schoolAnnouncementId, classId
        )?.category
            ?: AnnouncementCategory.NONE
    }

    fun findEducationOfficeAnnouncementCategory(
        educationOfficeAnnouncementId: Long,
        classId: Long
    ): AnnouncementCategory {
        return educationOfficeAnnouncementCategoryRepository.findByEducationOfficeAnnouncementIdAndClassroomId(
            educationOfficeAnnouncementId, classId
        )?.category
            ?: AnnouncementCategory.NONE
    }

    fun findSchoolAnnouncement(schoolAnnouncementId: Long): SchoolAnnouncement {
        return findSchoolAnnouncement { schoolAnnouncementRepository.findByIdOrNull(schoolAnnouncementId) }
    }

    fun findEducationOfficeAnnouncement(educationOfficeAnnouncementId: Long): EducationOfficeAnnouncement {
        return findEducationOfficeAnnouncement {
            educationOfficeAnnouncementRepository.findByIdOrNull(
                educationOfficeAnnouncementId
            )
        }
    }

    private fun findSchoolAnnouncement(function: () -> SchoolAnnouncement?): SchoolAnnouncement {
        return function() ?: throw AnnouncementException.AnnouncementNotFoundException("해당 가정통신문이 존재하지 않습니다.")
    }

    private fun findEducationOfficeAnnouncement(function: () -> EducationOfficeAnnouncement?): EducationOfficeAnnouncement {
        return function() ?: throw AnnouncementException.AnnouncementNotFoundException("해당 교육청 공지사항이 존재하지 않습니다.")
    }
}