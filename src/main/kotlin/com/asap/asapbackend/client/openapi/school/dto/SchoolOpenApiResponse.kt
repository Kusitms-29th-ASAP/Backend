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
    val ATPT_OFCDC_SC_NM: String,
    val SD_SCHUL_CODE: String,
    val SCHUL_NM: String,
    val ENG_SCHUL_NM: String,
    val SCHUL_KND_SC_NM: String,
    val LCTN_SC_NM: String,
    val JU_ORG_NM: String,
    val FOND_SC_NM: String,
    val ORG_RDNZC: String,
    val ORG_RDNMA: String,
    val ORG_RDNDA: String,
    val ORG_TELNO: String,
    val HMPG_ADRES: String,
    val COEDU_SC_NM: String,
    val ORG_FAXNO: String,
    val HS_SC_NM: String,
    val INDST_SPECL_CCCCL_EXST_YN: String,
    val HS_GNRL_BUSNS_SC_NM: String,
    val SPCLY_PURPS_HS_ORD_NM: String,
    val ENE_BFE_SEHF_SC_NM: String,
    val DGHT_SC_NM: String,
    val FOND_YMD: String,
    val FOAS_MEMRD: String,
    val LOAD_DTM: String
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