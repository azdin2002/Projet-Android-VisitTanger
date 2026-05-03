package com.groupe10.visittanger.domain.repository

import com.groupe10.visittanger.domain.model.Itinerary
import com.groupe10.visittanger.domain.model.ItineraryType
import kotlinx.coroutines.flow.Flow

interface ItineraryRepository {
    fun getAllItineraries(): Flow<List<Itinerary>>
    fun getItinerariesByType(type: ItineraryType): Flow<List<Itinerary>>
    suspend fun getItineraryById(id: String): Itinerary?
}
