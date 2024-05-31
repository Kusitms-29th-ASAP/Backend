package com.asap.asapbackend.domain.teacher.application.dto

import com.asap.asapbackend.domain.classroom.domain.vo.Grade

class GetTeacherInfo {

    data class Response(
        val schoolName: String,
        val grade: Grade,
        val className: String,
        val teacherName: String,
    )
}