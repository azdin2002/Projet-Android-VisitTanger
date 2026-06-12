package com.groupe10.visittanger.domain.usecase

import android.util.Log
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.repository.FavoriteRepository
import com.groupe10.visittanger.domain.repository.PlaceRepository
import com.groupe10.visittanger.domain.repository.VisitedPlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetRecommendedPlacesUseCase @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val favoriteRepository: FavoriteRepository,
    private val visitedPlaceRepository: VisitedPlaceRepository,
) {
    suspend operator fun invoke(userId: String): Flow<List<Place>> =
        combine(
            placeRepository.getPlaces(),
            favoriteRepository.getFavoritesByUser(userId),
            visitedPlaceRepository.getVisitedPlaces(userId),
        ) { places, favorites, visitedPlaces ->
            Log.d("Recommendation", "userId: $userId")
            Log.d("Recommendation", "Favorites: ${favorites.map { "${it.id}(${it.category})" }}")
            Log.d("Recommendation", "Visited: ${visitedPlaces.map { "${it.id}(${it.category})" }}")

            val result = recommendPlaces(
                places = places,
                favorites = favorites,
                visitedPlaces = visitedPlaces,
            )

            Log.d("Recommendation", "Final: ${result.map { "${it.id}(${it.category})" }}")
            result
        }

    private fun recommendPlaces(
        places: List<Place>,
        favorites: List<Place>,
        visitedPlaces: List<Place>,
    ): List<Place> {
        val preferredPlaces = favorites + visitedPlaces
        val sortedPlaces = places.sortedForRecommendation()

        if (preferredPlaces.isEmpty()) {
            val highlyRatedPlaces = sortedPlaces.filter { it.rating >= MIN_COLD_START_RATING }
            return (if (highlyRatedPlaces.size >= MAX_RECOMMENDATIONS) highlyRatedPlaces else sortedPlaces)
                .take(MAX_RECOMMENDATIONS)
        }

        val dominantCategories = preferredPlaces.dominantCategories()
        Log.d("Recommendation", "Dominant Categories: $dominantCategories")
        val excludedPlaceIds = preferredPlaces.map { it.id }.toSet()

        val filteredRecommended = sortedPlaces
            .filter { place ->
                place.category in dominantCategories && place.id !in excludedPlaceIds
            }

        if (filteredRecommended.size >= MAX_RECOMMENDATIONS) {
            return filteredRecommended.take(MAX_RECOMMENDATIONS)
        }

        // Fallback: Add top rated places from other categories that are not already in recommendations or excluded
        val currentIds = filteredRecommended.map { it.id }.toSet()
        val fallback = sortedPlaces
            .filter { it.id !in excludedPlaceIds && it.id !in currentIds }
            .take(MAX_RECOMMENDATIONS - filteredRecommended.size)

        return filteredRecommended + fallback
    }

    private fun List<Place>.dominantCategories(): Set<Category> {
        val categoryCounts = groupingBy { it.category }.eachCount()
        if (categoryCounts.isEmpty()) return emptySet()

        // Sort categories by count descending, then take top 2
        val sortedCategories = categoryCounts.toList()
            .sortedByDescending { it.second }
        
        val topCount = sortedCategories.first().second
        val topCategories = sortedCategories.filter { it.second == topCount }

        return if (topCategories.size > 2) {
            // If more than 2 categories are tied for first place, take only the first 2
            topCategories.take(2).map { it.first }.toSet()
        } else if (topCategories.size == 1 && sortedCategories.size > 1) {
            // If there's a clear #1, take #1 and #2 (even if #2 has fewer votes)
            sortedCategories.take(2).map { it.first }.toSet()
        } else {
            // Otherwise take all tied for top (which will be 1 or 2 categories)
            topCategories.map { it.first }.toSet()
        }
    }

    private fun List<Place>.sortedForRecommendation(): List<Place> =
        sortedWith(
            compareByDescending<Place> { it.rating }
                .thenBy { it.distanceKm ?: Double.MAX_VALUE },
        )

    private companion object {
        const val MAX_RECOMMENDATIONS = 5
        const val MIN_COLD_START_RATING = 4.5f
    }
}
