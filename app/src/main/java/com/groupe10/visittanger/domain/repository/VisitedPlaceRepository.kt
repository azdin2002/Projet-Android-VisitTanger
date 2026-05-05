package com.groupe10.visittanger.domain.repository

import kotlinx.coroutines.flow.Flow

interface VisitedPlaceRepository {
    suspend fun markAsVisited(placeId: String, userId: String? = null)
    fun getVisitedCount(userId: String): Flow<Int>
}
