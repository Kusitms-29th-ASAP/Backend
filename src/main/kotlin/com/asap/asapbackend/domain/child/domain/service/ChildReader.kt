package com.asap.asapbackend.domain.child.domain.service

import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.child.domain.model.PrimaryChild
import com.asap.asapbackend.domain.child.domain.repository.ChildRepository
import com.asap.asapbackend.domain.child.domain.repository.PrimaryChildRepository
import org.springframework.stereotype.Service

@Service
class ChildReader(
    private val childRepository: ChildRepository,
    private val primaryChildRepository: PrimaryChildRepository
) {
    fun findAllByIds(childIds: Set<Long>): Set<Child> {
        return childRepository.findAllById(childIds).toSet()
    }

    fun findPrimaryChildByParentId(parentId: Long): PrimaryChild {
        return primaryChildRepository.findByUserId(parentId)
    }
}