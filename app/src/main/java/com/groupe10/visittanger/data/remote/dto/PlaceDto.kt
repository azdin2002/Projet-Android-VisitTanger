package com.groupe10.visittanger.data.remote.dto

import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place

data class PlaceDto(
    val id: String = "",
    val name: String = "",
    val descriptionFr: String = "",
    val descriptionEn: String = "",
    val descriptionAr: String = "",
    val category: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
    val photos: List<String> = emptyList(),
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val openingHours: String? = null,
    val price: String? = null
) {
    fun toDomain() = Place(
        id = id,
        name = name,
        description = mapOf(
            "fr" to descriptionFr,
            "en" to descriptionEn,
            "ar" to descriptionAr,
        ),
        category = Category.valueOf(category),
        latitude = latitude,
        longitude = longitude,
        address = address,
        photos = photos,
        rating = rating,
        reviewCount = reviewCount,
        openingHours = openingHours,
        price = price,
    )
}
