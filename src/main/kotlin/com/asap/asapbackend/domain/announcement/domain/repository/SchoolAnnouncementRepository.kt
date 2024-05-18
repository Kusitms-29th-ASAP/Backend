package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SchoolAnnouncementRepository : JpaRepository<SchoolAnnouncement, Long> {
    @Query("""SELECT COALESCE(MAX(sa.idx), 0) 
        FROM SchoolAnnouncement sa
         join SchoolAnnouncement.schoolAnnouncementPage as sap on sap.id = sa.schoolAnnouncementPage.id and sap.school.id = :schoolId
""")
    fun findLastIndex(schoolId: Long): Int
}