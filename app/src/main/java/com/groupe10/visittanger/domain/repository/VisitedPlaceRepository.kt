package com.groupe10.visittanger.domain.repository

import com.groupe10.visittanger.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface VisitedPlaceRepository {
    suspend fun markAsVisited(placeId: String, userId: String? = null)
    fun getVisitedPlaces(userId: String): Flow<List<Place>>
    fun getVisitedCount(userId: String): Flow<Int>
}
