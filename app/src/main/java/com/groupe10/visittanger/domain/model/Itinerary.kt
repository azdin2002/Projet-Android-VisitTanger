package com.groupe10.visittanger.domain.model

data class Itinerary(
    val id: String,
    val title: Map<String, String>,
    val description: Map<String, String>,
    val duration: Map<String, String>,        // ex: "1 jour", "2 jours"
    val durationHours: Int,
    val type: ItineraryType,
    val places: List<ItineraryStop>,
    val coverPhoto: String = "",
    val difficulty: Map<String, String> = emptyMap(),
    val totalDistanceKm: Double = 0.0,
    val tags: List<String> = emptyList()
)
