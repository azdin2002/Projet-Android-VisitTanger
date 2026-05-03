package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.repository.UserRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        name: String? = null,
        photoUrl: String? = null
    ): Result<Unit> {
        return if (name != null) repository.updateDisplayName(name)
               else if (photoUrl != null) repository.updatePhotoUrl(photoUrl)
               else Result.success(Unit)
    }
}
