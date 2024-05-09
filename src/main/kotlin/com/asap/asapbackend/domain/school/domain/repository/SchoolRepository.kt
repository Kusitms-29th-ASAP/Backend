package com.asap.asapbackend.domain.school.domain.repository

import com.asap.asapbackend.domain.school.domain.model.School
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SchoolRepository : JpaRepository<School, Long> {
    @Query("SELECT s.schoolCode FROM School s WHERE s.schoolCode IN :schoolCodes")
    fun findSchoolCodeBySchoolCodeIn(schoolCodes: List<String>): List<String>

    fun findByNameContaining(keyword: String): List<School>
}