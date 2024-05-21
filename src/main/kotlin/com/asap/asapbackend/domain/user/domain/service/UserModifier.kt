package com.asap.asapbackend.domain.user.domain.service

import com.asap.asapbackend.domain.user.domain.model.User
import com.asap.asapbackend.domain.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserModifier(
    private val userRepository: UserRepository
) {
    fun update(user: User){
        userRepository.save(user)
    }
}