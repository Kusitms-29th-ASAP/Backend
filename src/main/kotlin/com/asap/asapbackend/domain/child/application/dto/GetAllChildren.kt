package com.asap.asapbackend.domain.child.application.dto

class GetAllChildren {
    data class Response(
        val childList: List<ChildInfo>
    )

    data class ChildInfo(
        val isPrimary: Boolean,
        val childId: Long,
        val childName: String,
        val schoolName: String,
        val grade: Int,
        val className: String,
    )
}