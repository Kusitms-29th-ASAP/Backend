package com.asap.asapbackend.domain.child.presentation

import com.asap.asapbackend.domain.child.application.ChildService
import com.asap.asapbackend.domain.child.application.dto.ChangeChildInfo
import com.asap.asapbackend.domain.child.application.dto.ChangePrimaryChild
import com.asap.asapbackend.domain.child.application.dto.GetAllChildren
import com.asap.asapbackend.domain.child.application.dto.GetChild
import org.springframework.web.bind.annotation.*

@RestController
class ChildController(
    private val childService: ChildService
) {
    @GetMapping(ChildApi.V1.BASE_URL)
    fun getAllChildren(): GetAllChildren.Response {
        return childService.getAllChildren()
    }

    @GetMapping(ChildApi.V1.CHILD)
    fun getChild(@PathVariable childId: Long): GetChild.Response {
        return childService.getChild(GetChild.Request(childId))
    }

    @PutMapping(ChildApi.V1.CHILD)
    fun changeChildInfo(
        @PathVariable childId: Long,
        @RequestBody request: ChangeChildInfo.Request
    ) {
        return childService.changeChildInfo(childId, request)
    }

    @PutMapping(ChildApi.V1.PRIMARY_CHILD)
    fun changePrimaryChild(@RequestBody request: ChangePrimaryChild.Request){
        return childService.changePrimaryChild(request)
    }
}