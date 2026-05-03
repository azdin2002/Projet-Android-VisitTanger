package com.groupe10.visittanger.domain.repository

import com.groupe10.visittanger.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun updateDisplayName(name: String): Result<Unit>
    suspend fun updatePhotoUrl(url: String): Result<Unit>
    suspend fun logout(): Result<Unit>
    fun getFavoritesCount(): Flow<Int>
}
