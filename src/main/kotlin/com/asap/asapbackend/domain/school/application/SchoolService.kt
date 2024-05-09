package com.asap.asapbackend.domain.school.application

import com.asap.asapbackend.domain.school.application.dto.GetSchools
import com.asap.asapbackend.domain.school.domain.service.SchoolReader
import org.springframework.stereotype.Service

@Service
class SchoolService(
    private val schoolReader: SchoolReader
) {
    fun getSchools(keyword: String): List<GetSchools.Response>{
        val schools = schoolReader.findBySchoolName(keyword)
        return schools.map {
            GetSchools.Response(it.name, it.address)
        }
    }
}