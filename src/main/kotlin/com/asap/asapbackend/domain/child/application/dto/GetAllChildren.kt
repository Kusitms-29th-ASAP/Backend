package com.asap.asapbackend.domain.child.application.dto

import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.classroom.domain.model.Classroom

class GetAllChildren {
    data class Response(
        val childList: List<ChildInfo>
    )

    data class ChildInfo(
        val isPrimary: Boolean,
        val childId: Long,
        val childName: String,
        val schoolName: String,
        val grade: Int,
        val className: String,
    )
    companion object {
        fun convertClassroomMapByChildToChildInfoList(classroomMap: Map<Child, Classroom> ,primaryChild : Child): List<ChildInfo> {
            return classroomMap.map {(child,classroom) ->
                ChildInfo(
                    child.id==primaryChild.id,
                    child.id,
                    child.name,
                    classroom.school.name,
                    classroom.grade.level,
                    classroom.className
                )
            }
        }
    }
}