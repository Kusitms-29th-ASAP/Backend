package com.asap.asapbackend.domain.school.domain.repository

import com.asap.asapbackend.domain.school.domain.model.School
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SchoolRepository : JpaRepository<School, Long> {
    @Query("SELECT s.schoolCode FROM School s WHERE s.schoolCode IN :schoolCodes")
    fun findSchoolCodeBySchoolCodeIn(schoolCodes: List<String>): List<String>

    override fun findAll(pageable: Pageable): Page<School>
}