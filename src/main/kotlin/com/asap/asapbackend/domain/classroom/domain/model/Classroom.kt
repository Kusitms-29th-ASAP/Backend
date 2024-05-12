package com.asap.asapbackend.domain.classroom.domain.model

import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.domain.teacher.domain.model.Teacher
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.*
import java.util.*

@Entity
class Classroom(
    grade: Int,
    className: String,
    school: School
) : BaseDateEntity() {
    @Column(
        nullable = false,
        columnDefinition = "varchar(255)"
    )
    @Enumerated(EnumType.STRING)
    val grade: Grade = Grade.convert(grade)

    val className: String = className

    @Embedded
    val year: Year = Year()

    val classCode: String = UUID.randomUUID().toString().split("-")[0]

    @ManyToOne(fetch = FetchType.LAZY)
    val school: School = school


    @OneToMany(mappedBy = "classroom")
    val childClassroomSet: MutableSet<ChildClassroom> = mutableSetOf()

    @OneToMany(mappedBy = "classroom")
    val teacherClassroomSet: MutableSet<TeacherClassroom> = mutableSetOf()

    fun addChild(child: Child) {
        childClassroomSet.add(ChildClassroom(child, this))
    }

    fun addTeacher(teacher: Teacher) {
        teacherClassroomSet.add(TeacherClassroom(teacher, this))
    }


    fun isSameClassroom(classroom: Classroom): Boolean {
        return this.grade == classroom.grade && this.className == classroom.className && this.school.id == classroom.school.id
    }

}