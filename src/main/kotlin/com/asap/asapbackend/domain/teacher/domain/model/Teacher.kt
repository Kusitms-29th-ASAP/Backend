package com.asap.asapbackend.domain.teacher.domain.model

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity

@Entity
class Teacher(
    username: String,
    password: String,
    name: String
) : BaseDateEntity() {

    val username: String = username

    var password: String = password
        protected set
    var name: String = name
        protected set
}