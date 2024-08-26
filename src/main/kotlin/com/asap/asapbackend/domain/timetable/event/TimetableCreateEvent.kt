package com.asap.asapbackend.domain.timetable.event

import com.asap.asapbackend.domain.timetable.domain.model.Subject


data class MultiSubjectCreateEvent(
    val subjects: Set<Subject>
) {
}
