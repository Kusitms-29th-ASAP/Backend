package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SchoolAnnouncementRepository : JpaRepository<SchoolAnnouncement, Long> {
    @Query(
        """
            SELECT COALESCE(MAX(sa.idx), 0) 
        FROM SchoolAnnouncement sa
         join sa.schoolAnnouncementPage as sap on sap.id = sa.schoolAnnouncementPage.id and sap.school.id = :schoolId
    """
    )
    fun findLastIndex(schoolId: Long): Int


    @Query(
        """
        SELECT sa
        FROM SchoolAnnouncement sa
        join sa.schoolAnnouncementPage as sap on sap.id = sa.schoolAnnouncementPage.id
        join sap.school as s on s.id = sap.school.id
        join Classroom as c on c.school.id = s.id and c.id = :classroomId
        ORDER BY sa.idx DESC
    """
    )
    fun findAllByClassroomId(classroomId: Long, pageable: Pageable): Page<SchoolAnnouncement>
}