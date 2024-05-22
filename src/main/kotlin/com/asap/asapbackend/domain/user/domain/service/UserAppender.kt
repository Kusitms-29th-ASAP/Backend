package com.asap.asapbackend.domain.user.domain.service

import com.asap.asapbackend.domain.user.domain.exception.UserException
import com.asap.asapbackend.domain.user.domain.model.User
import com.asap.asapbackend.domain.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserAppender(
    private val userRepository: UserRepository
) {

    fun appendUser(user: User){
        userRepository.findBySocialInfo_SocialId(user.socialInfo.socialId)?.let {
            throw UserException.UserAlreadyExistsException()
        }
        userRepository.save(user)
    }
}