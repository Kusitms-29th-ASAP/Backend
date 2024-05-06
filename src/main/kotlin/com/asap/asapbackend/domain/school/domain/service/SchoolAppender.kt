package com.asap.asapbackend.domain.school.domain.service

import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.domain.school.domain.repository.SchoolRepository
import org.springframework.stereotype.Service

@Service
class SchoolAppender(
        private val schoolRepository: SchoolRepository
){
    fun appendUniqueSchool(schools: List<School>) {
        val schoolCodes = schools.map { it.schoolCode }
        val existsSchools = schoolRepository.findSchoolCodeBySchoolCodeIn(schoolCodes)
        schools.forEach {
            if(existsSchools.contains(it.schoolCode).not()){
                schoolRepository.save(it)
            }
        }
    }

}