package com.asap.asapbackend.domain.child.application

import com.asap.asapbackend.domain.child.application.dto.ChangeChildInfo
import com.asap.asapbackend.domain.child.application.dto.ChangePrimaryChild
import com.asap.asapbackend.domain.child.application.dto.GetAllChildren
import com.asap.asapbackend.domain.child.application.dto.GetChild
import com.asap.asapbackend.domain.child.domain.exception.ChildException
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
        val userId = getCurrentUserId()
        val child = childReader.findById(request.childId)
        if(childReader.findAllByParentId(userId).contains(child)){
            val classroom = classroomReader.findByStudent(request.childId)
            return GetChild.toResponse(child, classroom)
        }else {
            throw ChildException.ChildAccessDeniedException()
        }
    }

    @Transactional
    fun changeChildInfo(childId: Long, request: ChangeChildInfo.Request) {
        val userId = getCurrentUserId()
        val child = childReader.findById(childId)
        childReader.findAllByParentId(userId).contains(child)
        if(childReader.findAllByParentId(userId).contains(child)){
            child.changeInfo(request.childName, request.birthday, request.allergies)
            childModifier.changeInfo(child)
        }else {
            throw ChildException.ChildAccessDeniedException()
        }
    }

    @Transactional
    fun changePrimaryChild (request: ChangePrimaryChild.Request){
        val userId = getCurrentUserId()

        childModifier.changePrimaryChild(userId,request.childId)
    }
}