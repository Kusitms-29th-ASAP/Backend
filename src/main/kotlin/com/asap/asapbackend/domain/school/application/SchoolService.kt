package com.asap.asapbackend.domain.school.application

import com.asap.asapbackend.domain.school.application.dto.GetSchools
import com.asap.asapbackend.domain.school.domain.service.SchoolReader
import org.springframework.stereotype.Service

@Service
class SchoolService(
    private val schoolReader: SchoolReader
) {
    fun getSchools(keyword: String): GetSchools.Response {
        val schools = schoolReader.findBySchoolName(keyword)
        val schoolResponse = schools.map {
            GetSchools.School(it.id, it.name, it.address)
        }
        return GetSchools.Response(schoolResponse)
    }
}