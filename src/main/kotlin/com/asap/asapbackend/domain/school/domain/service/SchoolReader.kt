package com.asap.asapbackend.domain.school.domain.service

import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.domain.school.domain.repository.SchoolRepository
import com.asap.asapbackend.global.util.CacheManager
import org.springframework.stereotype.Service

@Service
class SchoolReader(
    private val schoolRepository: SchoolRepository
) {
    private val schoolCache: Map<Long, School> = CacheManager.cacheByKey("SchoolCache") {
        schoolRepository.findAll().associateBy {
            it.id
        }
    }.toMap()


    fun findBySchoolName(keyword: String): List<School> {
        return schoolCache.values.filter {
            it.isContainingName(keyword)
        }.toList()
    }
}