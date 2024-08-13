package com.asap.asapbackend.infrastructure.jpa.menu.entity

import com.asap.asapbackend.infrastructure.jpa.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "menu",
    indexes = [
        Index(name = "idx_menu_school_id", columnList = "school_id"),
        Index(name = "idx_menu_school_and_day", columnList = "school_id, day", unique = true)
    ]
)
class MenuEntity(
    id: Long,
    schoolId: Long,
    day: LocalDate,
    foods: List<FoodEntity>,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
) : BaseEntity(createdAt, updatedAt) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id

    @Column(
        nullable = false,
        name = "school_id"

    )
    val schoolId: Long = schoolId

    val day: LocalDate = day

    @JdbcTypeCode(value = SqlTypes.JSON)
    var foods: List<FoodEntity> = foods



    fun changeFoods(foods: List<FoodEntity>?){
        foods?.let {
            this.foods = foods
        }
    }
}