package com.asap.asapbackend.client.openapi.school.dto

import com.asap.asapbackend.batch.school.SchoolInfoProvider


data class SchoolOpenApiResponse(
    val neisSchoolInfoJS: NeisSchoolInfoJS
)

data class NeisSchoolInfoJS(
    val list_total_count: Int,
    val row: List<SchoolInfo>?
)

data class SchoolInfo(
    val ATPT_OFCDC_SC_CODE: String,
    val SD_SCHUL_CODE: String,
    val SCHUL_NM: String,
    val ORG_RDNMA: String
) {
    fun toSchoolInfo(): SchoolInfoProvider.SchoolInfo {
        return SchoolInfoProvider.SchoolInfo(
            ATPT_OFCDC_SC_CODE,
            SD_SCHUL_CODE,
            SCHUL_NM,
            ORG_RDNMA
        )
    }
}