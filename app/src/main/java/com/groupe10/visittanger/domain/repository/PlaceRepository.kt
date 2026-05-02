package com.groupe10.visittanger.domain.repository

import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun getPlaces(): Flow<List<Place>>
    fun getPlacesByCategory(category: Category): Flow<List<Place>>
    fun searchPlaces(query: String): Flow<List<Place>>
    suspend fun getPlaceById(id: String): Place?
    suspend fun toggleFavorite(placeId: String)
}
