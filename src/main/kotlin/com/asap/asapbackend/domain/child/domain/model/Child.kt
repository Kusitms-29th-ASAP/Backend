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
    name: String,
    gender: Gender,
    birthday: LocalDate,
    allergies: Set<Allergy>,
    parent: User
):BaseDateEntity() {

    @Column(
        nullable = false,
    )
    var name: String = name

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "varchar(100)"
    )
    var gender: Gender = gender

    val birthday: LocalDate = birthday

    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    @JdbcTypeCode(SqlTypes.JSON)
    val allergies: MutableSet<Allergy> = allergies.toMutableSet()


    @ManyToOne(fetch = FetchType.LAZY)
    val parent: User = parent
}