package com.asap.asapbackend.domain.user.domain.service

import com.asap.asapbackend.domain.user.domain.exception.UserException
import com.asap.asapbackend.domain.user.domain.model.User
import com.asap.asapbackend.domain.user.domain.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserReader(
    private val userRepository: UserRepository
) {
    fun findBySocialIdOrNull(socialId: String): User? {
        return userRepository.findBySocialInfo_SocialId(socialId)
    }

    fun findById(id: Long): User {
        return findUser {
            userRepository.findByIdOrNull(id)
        }
    }

    private fun findUser(function: () -> User?): User {
        return function() ?: throw UserException.UserNotFoundException()
    }
}