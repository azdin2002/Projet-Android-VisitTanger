package com.groupe10.visittanger.data.mock

import com.groupe10.visittanger.domain.model.Review

object ReviewMockData {
    val reviews = mapOf(
        "1" to listOf(
            Review("r1", "Ahmed K.", null, 5f,
                "Vue magnifique sur la mer, incontournable !",
                "Mars 2026"),
            Review("r2", "Sophie M.", null, 4f,
                "Très beau site historique, guide recommandé.",
                "Février 2026"),
            Review("r3", "Youssef B.", null, 5f,
                "Endroit mythique de Tanger, à visiter absolument.",
                "Janvier 2026")
        ),
        "2" to listOf(
            Review("r4", "Marie L.", null, 5f,
                "Le coucher de soleil ici est inoubliable !",
                "Avril 2026"),
            Review("r5", "Karim A.", null, 5f,
                "Deux mers en un seul point, magique.",
                "Mars 2026")
        ),
        "3" to listOf(
            Review("r6", "Pierre D.", null, 4f,
                "Les grottes sont impressionnantes vues de l'intérieur.",
                "Mars 2026"),
            Review("r7", "Fatima Z.", null, 5f,
                "Lieu unique au monde, la fenêtre sur l'Afrique !",
                "Février 2026")
        )
    )

    fun getReviewsForPlace(placeId: String): List<Review> =
        reviews[placeId] ?: listOf(
            Review("r_default", "Visiteur anonyme", null, 4f,
                "Très bon endroit à visiter.", "2026")
        )
}
