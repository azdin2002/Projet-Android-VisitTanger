package com.groupe10.visittanger.data.remote.dto

import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place

data class PlaceDto(
    val id: String = "",
    val name: String = "",
    val description: Map<String, String> = emptyMap(),
    val category: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
    val photos: List<String> = emptyList(),
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val openingHours: String? = null,
    val price: String? = null,
    val distanceKm: Double? = null,
)

fun PlaceDto.toDomain(): Place {
    val safeCategory = runCatching { Category.valueOf(category.uppercase()) }.getOrDefault(Category.HISTORY)
    return Place(
        id = id,
        name = name,
        description = description,
        category = safeCategory,
        latitude = latitude,
        longitude = longitude,
        address = address,
        photos = photos,
        rating = rating,
        reviewCount = reviewCount,
        openingHours = openingHours,
        price = price,
        distanceKm = distanceKm,
    )
}
