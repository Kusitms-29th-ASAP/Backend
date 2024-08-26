package com.asap.asapbackend.infrastructure.jpa.todo.entity

import com.asap.asapbackend.domain.todo.domain.vo.Status
import com.asap.asapbackend.domain.todo.domain.vo.TodoType
import com.asap.asapbackend.infrastructure.jpa.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "todo",
    indexes = [
        Index(name = "idx_todo_child_id", columnList = "child_id")
    ]
)
class TodoEntity(
    id: Long,
    type: TodoType,
    description: String,
    childId: Long,
    deadline: LocalDate,
    isAssigned: Boolean,
    status: Status,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
) : BaseEntity(createdAt, updatedAt){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id;

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "varchar(50)"
    )
    val type: TodoType = type

    var description : String = description

    val deadline : LocalDate = deadline

    val isAssigned : Boolean = isAssigned

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "varchar(50)"
    )
    val status: Status = status


    @Column(name = "child_id")
    val childId: Long = childId



    fun changeDescription(description: String?){
        description?.let {
            this.description = description
        }
    }

}