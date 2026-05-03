package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<Place>> =
        repository.getAllFavorites()
}
