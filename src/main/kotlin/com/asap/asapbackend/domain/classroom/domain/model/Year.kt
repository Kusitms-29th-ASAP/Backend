package com.asap.asapbackend.domain.classroom.domain.model

import jakarta.persistence.Embeddable
import java.time.Year

@Embeddable
class Year{
    val year: Year = Year.now()
}