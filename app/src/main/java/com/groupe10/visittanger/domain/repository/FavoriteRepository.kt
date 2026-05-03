package com.groupe10.visittanger.domain.repository

import com.groupe10.visittanger.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorites(): Flow<List<Place>>
    suspend fun addFavorite(place: Place)
    suspend fun removeFavorite(placeId: String)
    suspend fun toggleFavorite(place: Place)
    fun isFavorite(placeId: String): Flow<Boolean>
    fun getFavoritesCount(): Flow<Int>
}
