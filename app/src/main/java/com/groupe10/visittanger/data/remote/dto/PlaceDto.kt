package com.groupe10.visittanger.data.remote.dto

import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place

data class PlaceDto(
    val id: String = "",
    val name: String = "",
    val nameFr: String = "",
    val nameEn: String = "",
    val nameAr: String = "",
    val descriptionFr: String = "",
    val descriptionEn: String = "",
    val descriptionAr: String = "",
    val teaserFr: String = "",
    val teaserEn: String = "",
    val teaserAr: String = "",
    val localTipFr: String = "",
    val localTipEn: String = "",
    val localTipAr: String = "",
    val category: String = "HISTORY",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
    val photos: List<String> = emptyList(),
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val openingHours: String? = null,
    val price: String? = null
) {
    fun toDomain(): Place {
        val resolvedNames = mapOf(
            "fr" to nameFr.ifBlank { name },
            "en" to nameEn.ifBlank { name },
            "ar" to nameAr.ifBlank { name },
        )
        return Place(
            id = id,
            name = resolvedNames["fr"]?.ifBlank { name } ?: name,
            names = resolvedNames,
            description = mapOf("fr" to descriptionFr, "en" to descriptionEn, "ar" to descriptionAr),
            teaser = mapOf("fr" to teaserFr, "en" to teaserEn, "ar" to teaserAr),
            localTips = mapOf("fr" to localTipFr, "en" to localTipEn, "ar" to localTipAr),
            category = Category.valueOf(category),
            latitude = latitude,
            longitude = longitude,
            address = address,
            photos = photos,
            rating = rating,
            reviewCount = reviewCount,
            openingHours = openingHours,
            price = price
        )
    }
}
