package com.asap.asapbackend.domain.school.domain.service

import com.asap.asapbackend.batch.school.model.SchoolResponse
import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.domain.school.domain.repository.SchoolRepository
import org.springframework.stereotype.Service

@Service
class SchoolAppender(
        private val schoolRepository: SchoolRepository
){
    fun addSchool(schools: List<SchoolResponse>) {
        for(school in schools){
            if(schoolRepository.findBySchoolCode(school.schoolCode) == null){
                val newSchool = School(
                        eduOfiiceCode = school.eduOfficeCode,
                        schoolCode = school.schoolCode,
                        name = school.school,
                        address = school.address
                )
                schoolRepository.save(newSchool)
            }
        }
    }
}