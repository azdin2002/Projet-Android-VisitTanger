package com.groupe10.visittanger.data.local.entity

import androidx.room.Entity
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place

@Entity(
    tableName = "favorites",
    primaryKeys = ["placeId", "userId"],
)
data class FavoriteEntity(
    val placeId: String,
    val userId: String,
    val name: String,
    val description: Map<String, String>,
    val category: String, // Category.name (enum -> String)
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val photos: String, // List<String> -> joinToString
    val rating: Float,
    val reviewCount: Int,
    val openingHours: String?,
    val price: String?,
    val distanceKm: Double?,
    val savedAt: Long = System.currentTimeMillis(),
)

fun FavoriteEntity.toDomainModel(): Place = Place(
    id = placeId,
    name = name,
    description = description,
    category = Category.valueOf(category),
    latitude = latitude,
    longitude = longitude,
    address = address,
    photos = if (photos.isEmpty()) emptyList()
    else photos.split(","),
    rating = rating,
    reviewCount = reviewCount,
    openingHours = openingHours,
    price = price,
    isFavorite = true,
    distanceKm = distanceKm,
)

fun Place.toFavoriteEntity(userId: String): FavoriteEntity = FavoriteEntity(
    placeId = id,
    userId = userId,
    name = name,
    description = description,
    category = category.name,
    latitude = latitude,
    longitude = longitude,
    address = address,
    photos = photos.joinToString(","),
    rating = rating,
    reviewCount = reviewCount,
    openingHours = openingHours,
    price = price,
    distanceKm = distanceKm,
)
