package com.asap.asapbackend.domain.child.application

import com.asap.asapbackend.domain.child.application.dto.ChangeChildInfo
import com.asap.asapbackend.domain.child.application.dto.ChangePrimaryChild
import com.asap.asapbackend.domain.child.application.dto.GetAllChildren
import com.asap.asapbackend.domain.child.application.dto.GetChild
import com.asap.asapbackend.domain.child.domain.service.ChildModifier
import com.asap.asapbackend.domain.child.domain.service.ChildReader
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomReader
import com.asap.asapbackend.global.security.getCurrentUserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChildService(
    private val childReader: ChildReader,
    private val classroomReader: ClassroomReader,
    private val childModifier: ChildModifier
) {
    fun getAllChildren(): GetAllChildren.Response {
        val userId = getCurrentUserId()
        val primaryChild = childReader.findPrimaryChild(userId)
        val children = childReader.findAllByParentId(userId)
        val classroomMapByChild = classroomReader.findClassroomMapByChild(children)
        val childrenInfoList =
            GetAllChildren.convertClassroomMapByChildToChildInfoList(classroomMapByChild, primaryChild)
        return GetAllChildren.Response(childrenInfoList)
    }

    fun getChild(request: GetChild.Request): GetChild.Response {
        val child = childReader.findById(request.childId)
        val classroom = classroomReader.findByStudent(request.childId)
        return GetChild.toResponse(child, classroom)
    }

    @Transactional
    fun changeChildInfo(childId: Long, request: ChangeChildInfo.Request) {
        val child = childReader.findById(childId)
        childModifier.changeInfo(child, request)
    }

    @Transactional
    fun changePrimaryChild (request: ChangePrimaryChild.Request){
        val userId = getCurrentUserId()
        childModifier.changePrimaryChild(userId,request.childId)
    }
}