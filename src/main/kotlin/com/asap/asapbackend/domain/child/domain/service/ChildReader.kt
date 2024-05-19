package com.asap.asapbackend.domain.child.domain.service

import com.asap.asapbackend.domain.child.domain.exception.ChildException
import com.asap.asapbackend.domain.child.domain.model.Child
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

    fun findPrimaryChild(userId: Long): Child {
        return findChild {
            primaryChildRepository.findChildByUserId(userId)
        }
    }

    fun findAllByParentId(parentId: Long): List<Child> {
        return childRepository.findByParentId(parentId)
    }

    private fun findChild(function: () -> Child?): Child {
        return function() ?: throw ChildException.ChildNotFoundException()
    }
}