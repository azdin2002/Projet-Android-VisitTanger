package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.repository.FavoriteRepository
import javax.inject.Inject

class ToggleFavoriteWithRepoUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(place: Place) =
        repository.toggleFavorite(place)
}
