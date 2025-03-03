package com.asap.asapbackend.domain.child.domain.service

import com.asap.asapbackend.domain.child.domain.exception.ChildException
import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.child.domain.repository.ChildRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChildReader(
    private val childRepository: ChildRepository,
) {
    fun findAllByIds(childIds: Set<Long>): Set<Child> {
        return childRepository.findAllById(childIds).toSet()
    }

    fun findPrimaryChild(userId: Long): Child {
        return findChild {
            childRepository.findPrimaryChildByParentId(userId)
        }
    }

    fun findAllByParentId(parentId: Long): List<Child> {
        return childRepository.findAllByParentId(parentId)
    }

    fun findById(childId: Long): Child {
        return findChild {
            childRepository.findByIdOrNull(childId)
        }
    }

    private fun findChild(function: () -> Child?): Child {
        return function() ?: throw ChildException.ChildNotFoundException()
    }
}