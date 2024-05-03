package com.asap.asapbackend.batch.school.application

import com.asap.asapbackend.domain.school.domain.service.SchoolAppender
import com.asap.asapbackend.batch.school.service.SchoolOepnApiClient
import org.springframework.stereotype.Service

@Service
class SchoolService(
        private val schoolReader: SchoolOepnApiClient,
        private val schoolAppender: SchoolAppender
){
    fun addSchool() {
        val school1 = schoolReader.getSchool1()
        val school2 = schoolReader.getSchool2()
        val school3 = schoolReader.getSchool3()
        schoolAppender.addSchool(school1)
        schoolAppender.addSchool(school2)
        schoolAppender.addSchool(school3)
    }
}