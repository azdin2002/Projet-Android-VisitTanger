package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> =
        repository.logout()
}
