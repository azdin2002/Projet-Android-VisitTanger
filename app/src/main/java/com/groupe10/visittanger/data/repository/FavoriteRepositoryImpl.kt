package com.groupe10.visittanger.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.groupe10.visittanger.data.local.dao.FavoriteDao
import com.groupe10.visittanger.data.local.entity.toDomainModel
import com.groupe10.visittanger.data.local.entity.toFavoriteEntity
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.repository.FavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val firebaseAuth: FirebaseAuth,
) : FavoriteRepository {

    private fun firebaseUidFlow(): Flow<String> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser?.uid.orEmpty())
        }
        firebaseAuth.addAuthStateListener(listener)
        trySend(firebaseAuth.currentUser?.uid.orEmpty())
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    private fun currentUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()

    override fun getAllFavorites(): Flow<List<Place>> =
        firebaseUidFlow().flatMapLatest { uid ->
            if (uid.isBlank()) flowOf(emptyList())
            else favoriteDao.getFavoritesByUser(uid).map { entities -> entities.map { it.toDomainModel() } }
        }

    override suspend fun addFavorite(place: Place) {
        val uid = currentUserId()
        if (uid.isBlank()) return
        favoriteDao.insertFavorite(place.toFavoriteEntity(uid))
    }

    override suspend fun removeFavorite(placeId: String) {
        val uid = currentUserId()
        if (uid.isBlank()) return
        favoriteDao.deleteFavoriteById(placeId, uid)
    }

    override suspend fun toggleFavorite(place: Place) {
        val uid = currentUserId()
        if (uid.isBlank()) return
        val existing = favoriteDao.getFavoriteById(place.id, uid)
        if (existing != null) {
            favoriteDao.deleteFavoriteById(place.id, uid)
        } else {
            favoriteDao.insertFavorite(place.toFavoriteEntity(uid))
        }
    }

    override fun isFavorite(placeId: String): Flow<Boolean> =
        firebaseUidFlow().flatMapLatest { uid ->
            if (uid.isBlank()) flowOf(false)
            else favoriteDao.isFavorite(placeId, uid)
        }

    override fun getFavoritesCount(): Flow<Int> =
        firebaseUidFlow().flatMapLatest { uid ->
            if (uid.isBlank()) flowOf(0)
            else favoriteDao.getFavoritesCount(uid)
        }
}
