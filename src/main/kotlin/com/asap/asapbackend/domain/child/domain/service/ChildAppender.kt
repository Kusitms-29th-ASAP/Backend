package com.asap.asapbackend.domain.child.domain.service

import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.child.domain.repository.ChildRepository
import org.springframework.stereotype.Service

@Service
class ChildAppender(
    private val childRepository: ChildRepository
) {
    fun appendChild(child: Child) {
        childRepository.save(child)
    }
}