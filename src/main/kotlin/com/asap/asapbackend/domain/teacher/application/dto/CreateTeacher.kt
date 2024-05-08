package com.asap.asapbackend.domain.teacher.application.dto

import com.asap.asapbackend.domain.classroom.domain.model.Grade
import com.asap.asapbackend.domain.teacher.domain.model.Teacher

class CreateTeacher{

    data class Request(
        val name: String,
        val schoolName: String,
        val grade: Grade,
        val className: String,
        val username: String, // 아이디
        val password: String,
    ){
        fun extractTeacher(encoder: (String) -> String): Teacher {
            return Teacher(
                name = name,
                username = username,
                password = encoder(password)
            )
        }

        fun extractClassroom(classroomQuery: (String, Grade, String) -> Unit) {
            classroomQuery(schoolName, grade, className)
        }
    }
}