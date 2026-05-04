package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<Unit> = userRepository.logout()
}
