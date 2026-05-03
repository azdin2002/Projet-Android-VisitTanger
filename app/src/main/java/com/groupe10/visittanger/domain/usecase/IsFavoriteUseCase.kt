package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(placeId: String): Flow<Boolean> =
        repository.isFavorite(placeId)
}
