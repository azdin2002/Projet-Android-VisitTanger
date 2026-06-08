package com.groupe10.visittanger.domain.model

import com.groupe10.visittanger.domain.util.localized

fun Itinerary.localizedTitle(lang: String): String = title.localized(lang)

fun Itinerary.localizedDescription(lang: String): String = description.localized(lang)

fun Itinerary.localizedDuration(lang: String): String = duration.localized(lang)

fun Itinerary.localizedDifficulty(lang: String): String = difficulty.localized(lang)

fun ItineraryStop.localizedDuration(lang: String): String = duration.localized(lang)

fun ItineraryStop.localizedTips(lang: String): String = tips.localized(lang)
