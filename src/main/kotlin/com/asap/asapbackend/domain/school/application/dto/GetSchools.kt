package com.asap.asapbackend.domain.school.application.dto

class GetSchools {
    data class Response(
        val schools: List<School>
    )
    data class School(
        val id: Long,
        val name: String?,
        val address: String?
    )
}