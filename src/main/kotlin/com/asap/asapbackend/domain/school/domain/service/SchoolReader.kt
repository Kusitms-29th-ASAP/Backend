package com.asap.asapbackend.domain.school.domain.service

import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.domain.school.domain.repository.SchoolRepository
import org.springframework.stereotype.Service

@Service
class SchoolReader(
    private val schoolRepository: SchoolRepository
) {
    fun findBySchoolName(keyword: String): List<School>{
        return schoolRepository.findByNameContaining(keyword)
    }
}