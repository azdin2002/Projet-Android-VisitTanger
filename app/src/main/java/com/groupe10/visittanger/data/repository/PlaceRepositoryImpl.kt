package com.groupe10.visittanger.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.groupe10.visittanger.data.local.dao.PlaceDao
import com.groupe10.visittanger.data.local.dao.FavoriteDao
import com.groupe10.visittanger.data.local.entity.toDomainModel
import com.groupe10.visittanger.data.local.entity.toPlaceEntity
import com.groupe10.visittanger.data.local.entity.toFavoriteEntity
import com.groupe10.visittanger.data.remote.dto.PlaceDto
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.repository.PlaceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class PlaceRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val placeDao: PlaceDao,
    private val favoriteDao: FavoriteDao,
    private val firebaseAuth: FirebaseAuth,
) : PlaceRepository {

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
            flow {
                val cached = placeDao.getAllPlaces().first()
                if (cached.isNotEmpty()) {
                    emit(cached.map { it.toDomainModel() })
                }

                val freshPlaces = runCatching {
                    val snapshot = firestore.collection("places").get().await()
                    snapshot.documents.mapNotNull { doc ->
                        doc.toObject(PlaceDto::class.java)
                            ?.copy(id = doc.id)
                            ?.toDomain()
                    }
                }.getOrElse { emptyList() }

                if (freshPlaces.isNotEmpty()) {
                    placeDao.insertAll(freshPlaces.map { it.toPlaceEntity() })
                    emit(freshPlaces)
                } else if (cached.isEmpty()) {
                    emit(emptyList())
                }
            }.combine(
                if (uid.isBlank()) flowOf(emptyList()) else favoriteDao.getFavoritesByUser(uid),
            ) { places, favorites ->
                val favoriteIds = favorites.map { it.placeId }.toSet()
                places.map { it.copy(isFavorite = it.id in favoriteIds) }
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
        val place = getPlaceById(placeId) ?: return
        val existing = favoriteDao.getFavoriteById(placeId, uid)
        if (existing != null) {
            favoriteDao.deleteFavoriteById(placeId, uid)
        } else {
            favoriteDao.insertFavorite(place.toFavoriteEntity(uid))
        }
    }
}
