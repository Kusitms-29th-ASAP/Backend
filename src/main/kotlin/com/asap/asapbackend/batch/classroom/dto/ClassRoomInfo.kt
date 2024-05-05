package com.asap.asapbackend.batch.classroom.dto

data class ClassRoomInfo (
        val ATPT_OFCDC_SC_CODE: String?,
        val ATPT_OFCDC_SC_NM: String?,
        val SD_SCHUL_CODE: String?,
        val SCHUL_NM: String?,
        val AY: String?,
        val GRADE: Int,
        val DGHT_CRSE_SC_NM: String?,
        val SCHUL_CRSE_SC_NM: String?,
        val ORD_SC_NM: String?,
        val DDDEP_NM: String?,
        val CLASS_NM: String,
        val LOAD_DTM: String?
)