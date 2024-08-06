package com.asap.asapbackend.domain.todo.domain.model

import com.asap.asapbackend.domain.todo.domain.vo.Status
import com.asap.asapbackend.domain.todo.domain.vo.TodoType
import com.asap.asapbackend.global.exception.validateProperty
import java.time.LocalDate
import java.time.LocalDateTime


class Todo (
    id: Long = 0L,
    type: TodoType,
    description: String,
    childId: Long,
    deadline: LocalDate,
    isAssigned: Boolean,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now()
){

    init {
        validateProperty(description.isNotBlank())
        validateProperty(deadline.isAfter(LocalDate.now().minusDays(1)))
    }

    val id: Long = id

    val type: TodoType = type

    val description : String = description

    val deadline : LocalDate = deadline

    val isAssigned : Boolean = isAssigned

    var status: Status = Status.INCOMPLETE
        protected set

    val childId : Long = childId

    val createdAt : LocalDateTime = createdAt
    var updatedAt : LocalDateTime = updatedAt

    fun changeStatus(){
        status = if(status == Status.COMPLETE) Status.INCOMPLETE else Status.COMPLETE
    }

}