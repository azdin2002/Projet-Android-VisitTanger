package com.groupe10.visittanger.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.groupe10.visittanger.data.local.dao.PlaceDao
import com.groupe10.visittanger.data.local.dao.VisitedPlaceDao
import com.groupe10.visittanger.data.local.entity.VisitedPlaceEntity
import com.groupe10.visittanger.data.local.entity.toDomainModel
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.repository.VisitedPlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VisitedPlaceRepositoryImpl @Inject constructor(
    private val visitedPlaceDao: VisitedPlaceDao,
    private val placeDao: PlaceDao,
    private val firebaseAuth: FirebaseAuth,
) : VisitedPlaceRepository {

    override suspend fun markAsVisited(placeId: String, userId: String?) {
        val uid = userId ?: firebaseAuth.currentUser?.uid.orEmpty()
        if (uid.isBlank() || placeId.isBlank()) return
        visitedPlaceDao.markAsVisited(
            VisitedPlaceEntity(
                placeId = placeId,
                userId = uid,
            ),
        )
    }

    override fun getVisitedPlaces(userId: String): Flow<List<Place>> {
        if (userId.isBlank()) return flowOf(emptyList())
        return combine(
            visitedPlaceDao.getVisitedPlaceIds(userId),
            placeDao.getAllPlaces(),
        ) { visitedPlaceIds, places ->
            val visitedIds = visitedPlaceIds.toSet()
            places
                .filter { it.id in visitedIds }
                .map { it.toDomainModel() }
        }
    }

    override fun getVisitedCount(userId: String): Flow<Int> {
        if (userId.isBlank()) return flowOf(0)
        return visitedPlaceDao.getVisitedCount(userId)
    }
}
