package com.asap.asapbackend.domain.child.domain.repository

import com.asap.asapbackend.domain.child.domain.model.Child
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChildRepository: JpaRepository<Child, Long> {
    @Query("""
        select c
        from Child c
        join PrimaryChild pc on c.id = pc.childId and pc.userId = :parentId
    """)
    fun findPrimaryChildByParentId(parentId: Long) : Child?

    fun findAllByParentId(parentId: Long) : List<Child>
}