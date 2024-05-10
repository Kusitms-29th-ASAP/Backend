package com.asap.asapbackend.fixture.user

import com.asap.asapbackend.domain.user.domain.model.User
import com.asap.asapbackend.domain.user.domain.service.UserAppender
import io.mockk.mockk
import org.springframework.test.util.ReflectionTestUtils


class MockUserAppender: UserAppender(mockk(relaxed = true)){
    private val userAppender: UserAppender = mockk(relaxed = true)
    override fun appendUser(user: User) {
        userAppender.appendUser(user)
        ReflectionTestUtils.setField(user, "id", 1L)
    }
}