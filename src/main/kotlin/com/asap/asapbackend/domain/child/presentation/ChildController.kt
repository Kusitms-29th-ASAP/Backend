package com.asap.asapbackend.domain.child.presentation

import com.asap.asapbackend.domain.child.application.ChildService
import com.asap.asapbackend.domain.child.application.dto.GetAllChildren
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ChildController(
    private val childService: ChildService
) {
    @GetMapping(ChildApi.V1.BASE_URL)
    fun getAllChildren(): GetAllChildren.Response {
        return childService.getAllChildren()
    }
}