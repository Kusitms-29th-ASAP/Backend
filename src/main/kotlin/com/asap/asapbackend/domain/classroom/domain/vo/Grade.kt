package com.asap.asapbackend.domain.classroom.domain.vo

import com.asap.asapbackend.global.exception.DefaultException

enum class Grade(
    val level : Int,
    val description: String
){
    FIRST(1,"1학년"),
    SECOND(2,"2학년"),
    THIRD(3,"3학년"),
    FOURTH(4,"4학년"),
    FIFTH(5,"5학년"),
    SIXTH(6,"6학년"),
    ;

    companion object {
        fun convert(level: Int): Grade {
            return entries.find { it.level == level } ?: throw DefaultException.IllegalPropertyException()
        }
    }
}