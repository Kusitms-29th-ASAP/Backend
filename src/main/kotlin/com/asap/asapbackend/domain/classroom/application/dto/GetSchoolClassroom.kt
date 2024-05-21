package com.asap.asapbackend.domain.classroom.application.dto

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.vo.Grade

class GetSchoolClassroom {

    data class Request(
        val schoolId: Long
    )

    data class Response(
        val classrooms: List<ClassroomInfo>
    )

    data class ClassroomInfo(
        val grade: Grade,
        val classNumbers: List<String>
    )

    companion object {
        fun convertClassroomToResponse(classrooms: List<Classroom>): Response {
            return Response(classrooms.groupBy { it.grade }
                .map { (grade, classrooms) ->
                    ClassroomInfo(
                        grade = grade,
                        classNumbers = classrooms.map { it.className }
                    )
                }.sortedBy {
                    it.grade
                }
            )
        }
    }
}