package com.asap.asapbackend.domain.child.domain.service

import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.child.domain.model.PrimaryChild
import com.asap.asapbackend.domain.child.domain.repository.ChildRepository
import com.asap.asapbackend.domain.child.domain.repository.PrimaryChildRepository
import org.springframework.stereotype.Service

@Service
class ChildAppender(
    private val childRepository: ChildRepository,
    private val primaryChildRepository: PrimaryChildRepository
) {
    fun appendChild(child: Child) {
        if(primaryChildRepository.existsByUserId(child.parent.id).not()){
            childRepository.save(child)
            primaryChildRepository.save(PrimaryChild(child.parent, child))
        }
        childRepository.save(child)
    }
}