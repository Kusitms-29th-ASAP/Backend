package com.asap.asapbackend.domain.announcement.domain.vo

enum class AnnouncementCategory(
    val description: String
) {
    NONE("미선택"),
    MENU("급식"),
    INTERNAL_EXTERNAL_PROGRAM("교내외 프로그램"),
    SCHOOL_MANAGEMENT("학교 운영"),
    HEALTH("보건"),
    SCHOOL_SCHEDULE("학교 일정"),
    EDUCATION_BENEFIT("교육 혜택"),
    LIFE_SAFE("생활/안전"),
    ETC("기타")
}