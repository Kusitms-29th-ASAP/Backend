package com.asap.asapbackend.domain.classroom.domain.model

import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class ChildClassroom(
    @ManyToOne(fetch = FetchType.LAZY)
    val student: Child,
    @ManyToOne(fetch = FetchType.LAZY)
    val classroom: Classroom
) :BaseDateEntity(){
}