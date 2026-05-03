package com.groupe10.visittanger.domain.model

enum class ItineraryType(
    val labelFr: String,
    val emoji: String,
    val color: Long
) {
    ONE_DAY("1 jour", "☀", 0xFF009966),
    WEEKEND("Week-end", "🗓", 0xFF185FA5),
    HISTORICAL("Historique", "🏛", 0xFF854F0B),
    NATURE("Nature", "🌿", 0xFF3B6D11),
    GASTRONOMY("Gastronomie", "🍽", 0xFF993C1D),
    CUSTOM("Personnalisé", "✏", 0xFF534AB7)
}
