package com.groupe10.visittanger.data.repository

import com.groupe10.visittanger.data.local.dao.FavoriteDao
import com.groupe10.visittanger.data.local.entity.toDomainModel
import com.groupe10.visittanger.data.local.entity.toFavoriteEntity
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override fun getAllFavorites(): Flow<List<Place>> =
        favoriteDao.getAllFavorites()
            .map { entities -> entities.map { it.toDomainModel() } }

    override suspend fun addFavorite(place: Place) =
        favoriteDao.insertFavorite(place.toFavoriteEntity())

    override suspend fun removeFavorite(placeId: String) =
        favoriteDao.deleteFavoriteById(placeId)

    override suspend fun toggleFavorite(place: Place) {
        val existing = favoriteDao.getFavoriteById(place.id)
        if (existing != null) {
            favoriteDao.deleteFavoriteById(place.id)
        } else {
            favoriteDao.insertFavorite(place.toFavoriteEntity())
        }
    }

    override fun isFavorite(placeId: String): Flow<Boolean> =
        favoriteDao.isFavorite(placeId)

    override fun getFavoritesCount(): Flow<Int> =
        favoriteDao.getFavoritesCount()
}
