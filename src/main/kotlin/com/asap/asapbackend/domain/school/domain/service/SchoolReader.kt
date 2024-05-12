package com.asap.asapbackend.domain.school.domain.service

import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.domain.school.domain.repository.SchoolRepository
import org.springframework.stereotype.Service

@Service
class SchoolReader(
    private val schoolRepository: SchoolRepository
) {
    private val schoolCache: MutableMap<Long, School> = mutableMapOf()

    fun findBySchoolName(keyword: String): List<School> {
        if (schoolCache.isEmpty()) {
            val allSchools = schoolRepository.findAll().associateBy {
                it.id
            }
            schoolCache.putAll(allSchools)
        }
        val schools = schoolCache.values
        return schools.filter {
            it.isContainingName(keyword)
        }.toList()
    }
}