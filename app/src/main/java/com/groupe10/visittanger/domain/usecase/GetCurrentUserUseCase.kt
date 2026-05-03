package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.model.User
import com.groupe10.visittanger.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<User?> =
        repository.getCurrentUser()
}
