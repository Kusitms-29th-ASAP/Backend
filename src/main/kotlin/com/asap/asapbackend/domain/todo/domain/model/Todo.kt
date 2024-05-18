package com.asap.asapbackend.domain.todo.domain.model

import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.todo.domain.vo.Status
import com.asap.asapbackend.domain.todo.domain.vo.TodoType
import com.asap.asapbackend.global.domain.BaseDateEntity
import com.asap.asapbackend.global.exception.validateProperty
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Todo (
    type: TodoType,
    description: String,
    child: Child,
    deadline: LocalDate,
    isAssigned: Boolean
):BaseDateEntity(){

    init {
        validateProperty(description.isNotBlank())
        validateProperty(deadline.isAfter(LocalDate.now().minusDays(1)))
    }

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "varchar(255)"
    )
    val type: TodoType = type

    val description : String = description

    val deadline : LocalDate = deadline

    val isAssigned : Boolean = isAssigned

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "varchar(255)"
    )
    var status: Status = Status.INCOMPLETE
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    val child : Child = child

    fun changeStatus(){
        status = if(status == Status.COMPLETE) Status.INCOMPLETE else Status.COMPLETE
    }

}