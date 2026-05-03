package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.repository.PlaceRepository
import javax.inject.Inject

class GetPlaceByIdUseCase @Inject constructor(
    private val repository: PlaceRepository
) {
    suspend operator fun invoke(id: String): Place? =
        repository.getPlaceById(id)
}
