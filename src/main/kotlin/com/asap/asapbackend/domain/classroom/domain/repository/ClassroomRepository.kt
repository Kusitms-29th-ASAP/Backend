package com.asap.asapbackend.domain.classroom.domain.repository

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.vo.Grade
import com.asap.asapbackend.domain.school.domain.model.School
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ClassroomRepository : JpaRepository<Classroom, Long> {
    fun findByGradeAndClassNameAndSchoolId(grade: Grade, classNumber: String, schoolId: Long): Classroom?
    fun findBySchoolAndGradeAndClassName(school: School, grade: Grade, classNumber: String): Classroom?

    fun findByClassCode(classCode: String): Classroom?

    @Query("""
        SELECT c
        FROM Classroom c
        join c.school s on s.name = :schoolName
        WHERE c.grade = :grade
        AND c.className = :classNumber
    """)
    fun findByGradeAndClassNameAndSchoolName(grade: Grade, classNumber: String, schoolName: String): Classroom?

    @Query("""
        SELECT c
        FROM Classroom c
        join TeacherClassroom tc on tc.classroom = c and tc.teacher.id = :teacherId
    """)
    fun findByTeacherId(teacherId: Long): Classroom?
}