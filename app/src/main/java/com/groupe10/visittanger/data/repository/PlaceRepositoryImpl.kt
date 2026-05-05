package com.groupe10.visittanger.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.groupe10.visittanger.data.local.dao.FavoriteDao
import com.groupe10.visittanger.data.mock.PlaceMockData
import com.groupe10.visittanger.data.local.entity.toFavoriteEntity
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.repository.PlaceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class PlaceRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val firebaseAuth: FirebaseAuth,
) : PlaceRepository {

    private val _places = MutableStateFlow(PlaceMockData.places)

    private fun firebaseUidFlow(): Flow<String> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser?.uid.orEmpty())
        }
        firebaseAuth.addAuthStateListener(listener)
        trySend(firebaseAuth.currentUser?.uid.orEmpty())
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    private fun currentUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()

    override fun getPlaces(): Flow<List<Place>> =
        firebaseUidFlow().flatMapLatest { uid ->
            combine(
                _places,
                if (uid.isBlank()) flowOf(emptyList()) else favoriteDao.getFavoritesByUser(uid),
            ) { places, favorites ->
                val favoriteIds = favorites.map { it.placeId }.toSet()
                places.map { place ->
                    place.copy(isFavorite = place.id in favoriteIds)
                }
            }
        }

    override fun getPlacesByCategory(category: Category): Flow<List<Place>> {
        return getPlaces().map { places ->
            places.filter { it.category == category }
        }
    }

    override fun searchPlaces(query: String): Flow<List<Place>> {
        return getPlaces().map { places ->
            places.filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    override suspend fun getPlaceById(id: String): Place? {
        return getPlaces().first().find { it.id == id }
    }

    override suspend fun toggleFavorite(placeId: String) {
        val uid = currentUserId()
        if (uid.isBlank()) return
        val place = _places.value.find { it.id == placeId } ?: return
        val existing = favoriteDao.getFavoriteById(placeId, uid)
        if (existing != null) {
            favoriteDao.deleteFavoriteById(placeId, uid)
        } else {
            favoriteDao.insertFavorite(place.toFavoriteEntity(uid))
        }
    }
}
