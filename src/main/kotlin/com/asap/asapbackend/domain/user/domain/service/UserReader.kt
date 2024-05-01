package com.asap.asapbackend.domain.user.domain.service

import com.asap.asapbackend.domain.user.domain.model.User
import com.asap.asapbackend.domain.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserReader(
    private val userRepository: UserRepository
) {

    fun findBySocialIdOrNull(socialId: String): User?{
        return userRepository.findBySocialId(socialId)
    }

}