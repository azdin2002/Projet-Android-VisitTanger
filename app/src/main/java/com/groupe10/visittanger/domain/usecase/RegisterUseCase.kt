package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.model.User
import com.groupe10.visittanger.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, name: String): Result<User> {
        return authRepository.registerWithEmail(email, password, name)
    }
}
