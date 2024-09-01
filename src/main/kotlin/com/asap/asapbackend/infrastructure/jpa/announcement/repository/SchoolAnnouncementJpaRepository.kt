package com.asap.asapbackend.infrastructure.jpa.announcement.repository

import com.asap.asapbackend.infrastructure.jpa.announcement.entity.SchoolAnnouncementEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SchoolAnnouncementJpaRepository : JpaRepository<SchoolAnnouncementEntity, Long>{
    @Query(
        """
            SELECT COALESCE(MAX(sa.idx), 0) 
        FROM SchoolAnnouncementEntity sa
         join SchoolAnnouncementPage as sap on sap.id = sa.schoolAnnouncementPageId and sap.school.id = :schoolId
    """
    )
    fun findLastIndex(schoolId: Long): Int

    @Query(
        """
        SELECT sa
        FROM SchoolAnnouncementEntity sa
        join SchoolAnnouncementPage as sap on sap.id = sa.schoolAnnouncementPageId
        join sap.school as s on s.id = sap.school.id
        join Classroom as c on c.school.id = s.id and c.id = :classroomId
        ORDER BY sa.idx DESC
    """
    )
    fun findAllByClassroomId(classroomId: Long, pageable: Pageable): Page<SchoolAnnouncementEntity>
}