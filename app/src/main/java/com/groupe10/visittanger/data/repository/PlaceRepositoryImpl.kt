package com.groupe10.visittanger.data.repository

import com.groupe10.visittanger.data.mock.PlaceMockData
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepositoryImpl @Inject constructor() : PlaceRepository {

    private val _places = MutableStateFlow(PlaceMockData.places)

    override fun getPlaces(): Flow<List<Place>> = _places

    override fun getPlacesByCategory(category: Category): Flow<List<Place>> {
        return _places.map { places ->
            places.filter { it.category == category }
        }
    }

    override fun searchPlaces(query: String): Flow<List<Place>> {
        return _places.map { places ->
            places.filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    override suspend fun getPlaceById(id: String): Place? {
        return _places.value.find { it.id == id }
    }

    override suspend fun toggleFavorite(placeId: String) {
        val currentList = _places.value
        val newList = currentList.map {
            if (it.id == placeId) it.copy(isFavorite = !it.isFavorite) else it
        }
        _places.value = newList
    }
}
