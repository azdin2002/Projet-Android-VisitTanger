package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.repository.PlaceRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: PlaceRepository
) {
    suspend operator fun invoke(placeId: String) =
        repository.toggleFavorite(placeId)
}
