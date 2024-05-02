package com.asap.asapbackend.domain.school.domain.model

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity

@Entity
class School(
        val eduOfiiceCode: String,
        val schoolCode: String,
        val name: String,
        val address: String
) : BaseDateEntity() {
    init {
        require(name.isNotBlank()) { "학교 이름은 빈 값이 될 수 없습니다." }
        require(address.isNotBlank()) { "주소는 빈 값이 될 수 없습니다." }
    }
}