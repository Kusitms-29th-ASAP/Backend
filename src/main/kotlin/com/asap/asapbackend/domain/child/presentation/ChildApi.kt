package com.asap.asapbackend.domain.child.presentation

object ChildApi {
    object V1{
        const val BASE_URL = "/api/v1/children"
        const val CHILD = "$BASE_URL/{childId}"
        const val PRIMARY_CHILD = "$BASE_URL/primary"
    }
}