package com.asap.asapbackend.domain.child.domain.service

import com.asap.asapbackend.domain.child.domain.exception.ChildException
import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.child.domain.repository.ChildRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChildReader(
    private val childRepository: ChildRepository
) {
    fun findAllByIds(childIds: Set<Long>): Set<Child> {
        return childRepository.findAllById(childIds).toSet()
    }
}