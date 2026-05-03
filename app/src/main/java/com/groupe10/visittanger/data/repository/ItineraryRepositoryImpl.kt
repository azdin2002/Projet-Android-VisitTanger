package com.groupe10.visittanger.data.repository

import com.groupe10.visittanger.data.mock.ItineraryMockData
import com.groupe10.visittanger.domain.model.Itinerary
import com.groupe10.visittanger.domain.model.ItineraryType
import com.groupe10.visittanger.domain.repository.ItineraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItineraryRepositoryImpl @Inject constructor() : ItineraryRepository {

    override fun getAllItineraries(): Flow<List<Itinerary>> =
        flowOf(ItineraryMockData.itineraries)

    override fun getItinerariesByType(type: ItineraryType): Flow<List<Itinerary>> =
        flowOf(ItineraryMockData.getItinerariesByType(type))

    override suspend fun getItineraryById(id: String): Itinerary? =
        ItineraryMockData.getItineraryById(id)
}
