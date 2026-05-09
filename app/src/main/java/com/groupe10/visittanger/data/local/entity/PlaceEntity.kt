package com.groupe10.visittanger.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: Map<String, String>,
    val category: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val photos: String,
    val rating: Float,
    val reviewCount: Int,
    val openingHours: String?,
    val price: String?,
    val distanceKm: Double?,
)

fun PlaceEntity.toDomainModel(): Place = Place(
    id = id,
    name = name,
    description = description,
    category = Category.valueOf(category),
    latitude = latitude,
    longitude = longitude,
    address = address,
    photos = if (photos.isEmpty()) emptyList() else photos.split(","),
    rating = rating,
    reviewCount = reviewCount,
    openingHours = openingHours,
    price = price,
    distanceKm = distanceKm,
)

fun Place.toPlaceEntity(): PlaceEntity = PlaceEntity(
    id = id,
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
