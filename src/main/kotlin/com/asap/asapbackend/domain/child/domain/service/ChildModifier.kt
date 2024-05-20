package com.asap.asapbackend.domain.child.domain.service

import com.asap.asapbackend.domain.child.application.dto.ChangeChildInfo
import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.child.domain.repository.ChildRepository
import com.asap.asapbackend.domain.child.domain.repository.PrimaryChildRepository
import org.springframework.stereotype.Service

@Service
class ChildModifier (
    private val childRepository: ChildRepository,
    private val primaryChildRepository: PrimaryChildRepository
) {
    fun changeInfo(child: Child, request: ChangeChildInfo.Request) {
        child.changeInfo(request.childName,request.birthday,request.allergies)
        childRepository.save(child)
    }

    fun changePrimaryChild(userId: Long, childId: Long){
        val primaryChild = primaryChildRepository.findByUserId(userId)
        primaryChild.changePrimaryChild(childId)
        primaryChildRepository.save(primaryChild)
    }
}