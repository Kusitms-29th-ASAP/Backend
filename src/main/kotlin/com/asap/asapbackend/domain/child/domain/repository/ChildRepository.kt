package com.asap.asapbackend.domain.child.domain.repository

import com.asap.asapbackend.domain.child.domain.model.Child
import org.springframework.data.jpa.repository.JpaRepository

interface ChildRepository: JpaRepository<Child, Long> {
}