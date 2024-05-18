package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class SchoolAnnouncementJdbcRepository (
    private val jdbcTemplate: JdbcTemplate,
    private val objectMapper: ObjectMapper
){

    fun insertBatch(announcements: Set<SchoolAnnouncement>){
        jdbcTemplate.batchUpdate(INSERT_ANNOUNCEMENT, announcements.map {
            arrayOf(
                it.schoolAnnouncementPage.id,
                it.idx,
                it.title,
                objectMapper.writeValueAsString(it.imageUrls),
                objectMapper.writeValueAsString(it.summaries)
            )
        })
    }

    companion object{
        private const val INSERT_ANNOUNCEMENT = """
            INSERT INTO school_announcement(
                school_announcement_page_id,
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
                ?,
                now(),
                now()
            )
        """
    }
}