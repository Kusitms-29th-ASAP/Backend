package com.asap.asapbackend.domain.child.application

import com.asap.asapbackend.domain.child.application.dto.GetAllChildren
import com.asap.asapbackend.domain.child.domain.service.ChildReader
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomReader
import com.asap.asapbackend.global.security.getCurrentUserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChildService(
    private val childReader: ChildReader,
    private val classroomReader: ClassroomReader
) {
    fun getAllChildren(): GetAllChildren.Response {
        val userId = getCurrentUserId()
        val primaryChild = childReader.findPrimaryChild(userId)
//        val childrenInfo = childReader.findAllByParentId(userId).map {
//            val classroom = classroomReader.findByStudent(it.id)
//            GetAllChildren.ChildInfo(
//                it.id == primaryChild.id,
//                it.id,
//                it.name,
//                classroom.school.name,
//                classroom.grade.level,
//                classroom.className
//                )
//        }
        println("두번")
        val children = childReader.findAllByParentId(userId)
        println("세번")
        val childrenInfo = classroomReader.findClassroomMapByStudentId(children.map { it.id })
            .flatMap {(childId,classroom) ->
            children.map {
                GetAllChildren.ChildInfo(
                    childId==primaryChild.id,
                    it.id,
                    it.name,
                    classroom.school.name,
                    classroom.grade.level,
                    classroom.className
                )
            }
        }
        return GetAllChildren.Response(childrenInfo)
    }
}