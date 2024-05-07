package com.asap.asapbackend.batch.classroom

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.school.domain.model.School

interface ClassroomInfoProvider {
    fun retrieveClassroomInfo(batchSize: Int, pageNumber: Int): ClassroomDataContainer

    data class ClassroomDataContainer(
        val classroomInfo: List<ClassroomInfo>,
        val hasNext: Boolean
    )

    data class ClassroomInfo(
        val school: School,
        val grade: Int,
        val classNumber: String
    ) {
        fun toClassroom(): Classroom {
            return Classroom(
                school = school,
                grade = grade,
                className = classNumber
            )
        }
    }
}

fun List<ClassroomInfoProvider.ClassroomInfo>.toClassrooms(): List<Classroom> {
    return this.map { it.toClassroom() }
}