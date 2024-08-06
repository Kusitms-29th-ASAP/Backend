package com.asap.asapbackend.global.util

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

object HttpContextUtils {

    fun getHttpRequest(): HttpServletRequest {
        return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
    }
}