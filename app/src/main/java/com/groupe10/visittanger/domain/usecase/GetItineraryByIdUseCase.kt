package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.model.Itinerary
import com.groupe10.visittanger.domain.repository.ItineraryRepository
import javax.inject.Inject

class GetItineraryByIdUseCase @Inject constructor(
    private val repository: ItineraryRepository
) {
    suspend operator fun invoke(id: String): Itinerary? =
        repository.getItineraryById(id)
}
