package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class OfficeEducationAnnouncementJdbcRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val objectMapper: ObjectMapper
) {

    fun insertBatch(announcements: Set<EducationOfficeAnnouncement>){
        jdbcTemplate.batchUpdate(INSERT_EDUCATION_OFFICE_ANNOUNCEMENT, announcements.map {
            arrayOf(
                it.idx,
                it.title,
                objectMapper.writeValueAsString(it.imageUrls),
                objectMapper.writeValueAsString(it.summaries)
            )
        })
    }


    companion object{
        const val INSERT_EDUCATION_OFFICE_ANNOUNCEMENT = """
            INSERT INTO education_office_announcement(
                idx,
                title,
                image_urls,
                summaries,
                created_at,
                updated_at
            ) VALUES (
                ?,
                ?,
                ?,
                ?,
                now(),
                now()
            )
        """
    }
}