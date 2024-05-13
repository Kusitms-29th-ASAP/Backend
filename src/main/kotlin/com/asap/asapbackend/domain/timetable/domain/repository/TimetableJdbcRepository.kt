package com.asap.asapbackend.domain.timetable.domain.repository

import com.asap.asapbackend.domain.timetable.domain.model.Timetable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class TimetableJdbcRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun insertBatch(timetables: Collection<Timetable>) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO timetable (subject_id, day, time, created_at, updated_at) VALUES (?, ?, ?, now(),  now())",
            timetables.map {
                arrayOf(it.subject.id, it.day, it.time)
            }
        )
    }
}