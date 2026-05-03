package com.groupe10.visittanger.data.mock

import com.groupe10.visittanger.domain.model.Itinerary
import com.groupe10.visittanger.domain.model.ItineraryStop
import com.groupe10.visittanger.domain.model.ItineraryType

private val places = PlaceMockData.places

object ItineraryMockData {
    val itineraries = listOf(
        // Itinéraire 1: 1 jour à Tanger
        Itinerary(
            id = "itin_1",
            title = "1 jour à Tanger",
            description = "L'essentiel de Tanger en une journée. Des ruelles de la médina aux falaises du Cap Spartel.",
            duration = "1 jour",
            durationHours = 8,
            type = ItineraryType.ONE_DAY,
            coverPhoto = places[3].photos.firstOrNull() ?: "",
            difficulty = "Facile",
            totalDistanceKm = 18.5,
            tags = listOf("Culture", "Nature", "Incontournable"),
            places = listOf(
                ItineraryStop(1, places[3], "1h30", "Commencez tôt pour éviter la foule", "09:00"),
                ItineraryStop(2, places[4], "1h", "Goûtez le thé à la menthe", "10:30"),
                ItineraryStop(3, places[0], "2h", "Vue panoramique imprenable", "12:00"),
                ItineraryStop(4, places[6], "1h", "Déjeuner avec vue sur la mer", "14:00"),
                ItineraryStop(5, places[1], "2h", "Coucher de soleil magique", "16:00")
            )
        ),
        // Itinéraire 2: Week-end complet
        Itinerary(
            id = "itin_2",
            title = "Week-end à Tanger",
            description = "Deux jours pour explorer Tanger en profondeur. Histoire, nature et gastronomie au programme.",
            duration = "2 jours",
            durationHours = 16,
            type = ItineraryType.WEEKEND,
            coverPhoto = places[0].photos.firstOrNull() ?: "",
            difficulty = "Modéré",
            totalDistanceKm = 35.0,
            tags = listOf("Complet", "Histoire", "Nature", "Food"),
            places = listOf(
                ItineraryStop(1, places[3], "2h", "Jour 1 — Médina le matin", "09:00"),
                ItineraryStop(2, places[0], "1h30", "Jour 1 — Kasbah après-midi", "11:30"),
                ItineraryStop(3, places[4], "1h", "Jour 1 — Grand Socco shopping", "14:00"),
                ItineraryStop(4, places[6], "1h30", "Jour 1 — Café Hafa au coucher du soleil", "17:00"),
                ItineraryStop(5, places[1], "2h", "Jour 2 — Cap Spartel matin", "09:00"),
                ItineraryStop(6, places[2], "2h", "Jour 2 — Grottes d'Hercule", "11:30"),
                ItineraryStop(7, places[5], "2h", "Jour 2 — Plage Malabata après-midi", "15:00")
            )
        ),
        // Itinéraire 3: Tanger historique
        Itinerary(
            id = "itin_3",
            title = "Tanger historique",
            description = "Plongez dans l'histoire millénaire de Tanger. Médina, Kasbah et monuments incontournables.",
            duration = "1 jour",
            durationHours = 7,
            type = ItineraryType.HISTORICAL,
            coverPhoto = places[0].photos.firstOrNull() ?: "",
            difficulty = "Facile",
            totalDistanceKm = 5.0,
            tags = listOf("Histoire", "Culture", "Architecture"),
            places = listOf(
                ItineraryStop(1, places[3], "2h", "Perdez-vous dans les ruelles", "09:00"),
                ItineraryStop(2, places[7], "1h", "Place mythique de la médina", "11:00"),
                ItineraryStop(3, places[0], "2h", "Musée de la Kasbah inclus", "12:30"),
                ItineraryStop(4, places[4], "1h30", "Marché et artisanat local", "15:00")
            )
        ),
        // Itinéraire 4: Nature et évasion
        Itinerary(
            id = "itin_4",
            title = "Nature et évasion",
            description = "Les plus beaux espaces naturels autour de Tanger. Falaises, grottes et plages sauvages.",
            duration = "1 jour",
            durationHours = 8,
            type = ItineraryType.NATURE,
            coverPhoto = places[1].photos.firstOrNull() ?: "",
            difficulty = "Modéré",
            totalDistanceKm = 40.0,
            tags = listOf("Nature", "Plages", "Randonnée"),
            places = listOf(
                ItineraryStop(1, places[1], "2h", "Arrivez tôt pour voir le lever de soleil", "08:00"),
                ItineraryStop(2, places[2], "2h", "Explorez les deux côtés des grottes", "10:30"),
                ItineraryStop(3, places[5], "3h", "Baignade et détente", "13:30")
            )
        ),
        // Itinéraire 5: Gastronomie tangéroise
        Itinerary(
            id = "itin_5",
            title = "Saveurs de Tanger",
            description = "Un tour gourmand de Tanger. Thé à la menthe, tagines et spécialités locales.",
            duration = "1 jour",
            durationHours = 6,
            type = ItineraryType.GASTRONOMY,
            coverPhoto = places[6].photos.firstOrNull() ?: "",
            difficulty = "Facile",
            totalDistanceKm = 8.0,
            tags = listOf("Food", "Culture", "Marché"),
            places = listOf(
                ItineraryStop(1, places[4], "1h30", "Marché du matin, fruits et épices frais", "09:00"),
                ItineraryStop(2, places[7], "1h", "Café traditionnel au Petit Socco", "11:00"),
                ItineraryStop(3, places[6], "2h", "Déjeuner légendaire avec vue mer", "13:00"),
                ItineraryStop(4, places[3], "1h30", "Pâtisseries et thé dans la médina", "16:00")
            )
        )
    )

    fun getItineraryById(id: String): Itinerary? =
        itineraries.find { it.id == id }

    fun getItinerariesByType(type: ItineraryType): List<Itinerary> =
        itineraries.filter { it.type == type }
}
