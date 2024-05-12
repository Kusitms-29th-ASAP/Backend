package com.asap.asapbackend.domain.classroom.application.dto

import com.asap.asapbackend.domain.classroom.domain.vo.AnnouncementDescription
import com.asap.asapbackend.domain.todo.domain.vo.TodoType
import com.asap.asapbackend.global.exception.DefaultException
import java.time.LocalDate

class CreateAnnouncement {

    data class Request(
        val announcementDetails: List<AnnouncementDetails>,
        val writeDate: LocalDate
    ){
        init {
            if(announcementDetails.isEmpty()){
                throw DefaultException.IllegalPropertyException()
            }
        }

        fun toAnnouncementDescription() = announcementDetails.map {
            AnnouncementDescription(
                it.description
            )
        }
    }

    data class AnnouncementDetails(
        val description: String,
        val isLinkedWithTodo: Boolean,
        val todoType: TodoType,
        val deadline: LocalDate
    ) {
        init {
            if(todoType == TodoType.NONE && isLinkedWithTodo) {
                throw DefaultException.IllegalPropertyException()
            }
            if(description.isBlank()){
                throw DefaultException.IllegalPropertyException()
            }
        }
    }
}