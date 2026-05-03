package com.groupe10.visittanger.domain.model

data class ItineraryStop(
    val order: Int,
    val place: Place,
    val duration: String,       // ex: "1h30", "2h"
    val tips: String = "",      // conseil pour ce lieu
    val arrivalTime: String = "" // ex: "09:00", "14:30"
)
