package com.asap.asapbackend.domain.child.domain.model

import com.asap.asapbackend.domain.menu.domain.model.Allergy
import com.asap.asapbackend.domain.user.domain.model.User
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDate

@Entity
class Child(
    @Column(
        nullable = false,
    )
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "varchar(100)"
    )
    var gender: Gender,

    val birthday: LocalDate,

    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    @JdbcTypeCode(SqlTypes.JSON)
    val allergies: List<Allergy>,


    @ManyToOne(fetch = FetchType.LAZY)
    val parent: User

):BaseDateEntity() {
}