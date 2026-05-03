package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.model.Itinerary
import com.groupe10.visittanger.domain.repository.ItineraryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItinerariesUseCase @Inject constructor(
    private val repository: ItineraryRepository
) {
    operator fun invoke(): Flow<List<Itinerary>> =
        repository.getAllItineraries()
}
