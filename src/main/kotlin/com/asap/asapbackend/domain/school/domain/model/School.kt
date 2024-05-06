package com.asap.asapbackend.domain.school.domain.model

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity

@Entity
class School(
    eduOfficeCode: String,
    schoolCode: String,
    name: String,
    address: String
) : BaseDateEntity() {
    init {
        require(name.isNotBlank()) { "학교 이름은 빈 값이 될 수 없습니다." }
        require(address.isNotBlank()) { "주소는 빈 값이 될 수 없습니다." }
        require(eduOfficeCode.isNotBlank()) { "교육청 코드는 빈 값이 될 수 없습니다." }
        require(schoolCode.isNotBlank()) { "학교 코드는 빈 값이 될 수 없습니다." }
    }

    val eduOfficeCode: String = eduOfficeCode
    val schoolCode: String = schoolCode
    val name: String = name
    val address: String = address
}