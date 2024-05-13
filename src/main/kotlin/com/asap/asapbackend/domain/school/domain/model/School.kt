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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as School

        if (eduOfficeCode != other.eduOfficeCode) return false
        if (schoolCode != other.schoolCode) return false
        if (name != other.name) return false
        if (address != other.address) return false

        return true
    }

    override fun hashCode(): Int {
        var result = eduOfficeCode.hashCode()
        result = 31 * result + schoolCode.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + address.hashCode()
        return result
    }



    fun isContainingName(keyword: String): Boolean {
        return name.contains(keyword)
    }
}