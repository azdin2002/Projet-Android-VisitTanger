package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlacesUseCase @Inject constructor(
    private val repository: PlaceRepository
) {
    operator fun invoke(): Flow<List<Place>> = repository.getPlaces()
}
