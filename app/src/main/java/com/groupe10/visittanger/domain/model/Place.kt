package com.groupe10.visittanger.domain.model

data class Place(
    val id: String,
    val name: String,
    val description: Map<String, String>,
    val category: Category,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val photos: List<String>,
    val rating: Float,
    val reviewCount: Int,
    val openingHours: String? = null,
    val price: String? = null,
    val isFavorite: Boolean = false,
    val distanceKm: Double? = null
)
