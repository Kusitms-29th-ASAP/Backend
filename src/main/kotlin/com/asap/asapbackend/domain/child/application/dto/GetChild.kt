package com.asap.asapbackend.domain.child.application.dto

import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.menu.domain.model.Allergy
import java.time.LocalDate

class GetChild {
    data class Request(
        val childId : Long
    )
    data class Response(
        val childName: String,
        val schoolName: String,
        val grade: Int,
        val classroomName: String,
        val birthday : LocalDate,
        val allergies : List<Allergy>
    )
    companion object{
        fun toResponse(child: Child, classroom: Classroom) : Response{
            return Response(
                child.name,
                classroom.school.name,
                classroom.grade.level,
                classroom.className,
                child.birthday,
                child.allergies.toList()
            )
        }
    }
}