package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.model.User
import com.groupe10.visittanger.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.loginWithEmail(email, password)
    }
}
