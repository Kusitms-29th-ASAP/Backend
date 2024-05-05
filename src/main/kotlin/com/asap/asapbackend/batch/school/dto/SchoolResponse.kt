package com.asap.asapbackend.batch.school.dto

data class SchoolResponse(
        val eduOfficeCode: String, //ATPT_OFCDC_SC_CODE
        val schoolCode: String, //SD_SCHUL_CODE
        val school: String, //SCHUL_NM
        val address:String //ORG_RDNMA
)
