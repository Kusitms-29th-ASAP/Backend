package com.asap.asapbackend.batch.school

import com.asap.asapbackend.client.openapi.school.dto.SchoolInfo
import com.asap.asapbackend.domain.school.domain.model.School

interface SchoolInfoProvider {

    fun retrieveSchoolInfo(batchSize: Int, startIndex: Int): SchoolDataContainer

    data class SchoolDataContainer(
        val schoolInfo: List<SchoolInfo>,
        val hasNext: Boolean
    )

    data class SchoolInfo(
        val eduOfficeCode: String, //ATPT_OFCDC_SC_CODE
        val schoolCode: String, //SD_SCHUL_CODE
        val school: String, //SCHUL_NM
        val address:String //ORG_RDNMA
    ){
        fun toSchool(): School {
            return School(
                eduOfficeCode = eduOfficeCode,
                schoolCode = schoolCode,
                name = school,
                address = address
            )
        }

    }
}

fun List<SchoolInfoProvider.SchoolInfo>.toSchools(): List<School> {
    return this.map { it.toSchool() }
}