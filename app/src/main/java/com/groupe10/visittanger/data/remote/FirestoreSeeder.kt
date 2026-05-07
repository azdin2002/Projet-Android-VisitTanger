package com.groupe10.visittanger.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreSeeder @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    suspend fun seedPlacesIfEmpty() {
        runCatching {
            val placesRef = firestore.collection(COLLECTION_PLACES)
            val existing = placesRef.limit(1).get().await()
            if (!existing.isEmpty) return

            val places = listOf(
                hashMapOf(
                    "id" to "1",
                    "name" to "Kasbah de Tanger",
                    "descriptionFr" to "Ancienne citadelle dominant la médina",
                    "descriptionEn" to "Ancient citadel overlooking the medina",
                    "descriptionAr" to "قلعة قديمة تطل على المدينة القديمة",
                    "category" to "HISTORY",
                    "latitude" to 35.7907,
                    "longitude" to -5.8144,
                    "address" to "Kasbah, Tanger",
                    "photos" to listOf("https://example.com/kasbah.jpg"),
                    "rating" to 4.8,
                    "reviewCount" to 342,
                ),
                hashMapOf(
                    "id" to "2",
                    "name" to "Cap Spartel",
                    "descriptionFr" to "Phare et panorama sur l'Atlantique",
                    "descriptionEn" to "Lighthouse with Atlantic Ocean views",
                    "descriptionAr" to "منارة وإطلالة على المحيط الأطلسي",
                    "category" to "NATURE",
                    "latitude" to 35.7863,
                    "longitude" to -5.9248,
                    "address" to "Cap Spartel, Tanger",
                    "photos" to listOf("https://example.com/cap-spartel.jpg"),
                    "rating" to 4.9,
                    "reviewCount" to 521,
                ),
                hashMapOf(
                    "id" to "3",
                    "name" to "Grottes d'Hercule",
                    "descriptionFr" to "Grottes naturelles mythiques au bord de mer",
                    "descriptionEn" to "Mythical sea-side natural caves",
                    "descriptionAr" to "مغارات طبيعية أسطورية على شاطئ البحر",
                    "category" to "NATURE",
                    "latitude" to 35.7707,
                    "longitude" to -5.9215,
                    "address" to "Achakar, Tanger",
                    "photos" to listOf("https://example.com/hercules-cave.jpg"),
                    "rating" to 4.7,
                    "reviewCount" to 418,
                ),
                hashMapOf(
                    "id" to "4",
                    "name" to "Médina de Tanger",
                    "descriptionFr" to "Quartier historique avec ruelles traditionnelles",
                    "descriptionEn" to "Historic district with traditional alleys",
                    "descriptionAr" to "حي تاريخي بأزقة تقليدية",
                    "category" to "HISTORY",
                    "latitude" to 35.7896,
                    "longitude" to -5.8137,
                    "address" to "Médina, Tanger",
                    "photos" to listOf("https://example.com/medina.jpg"),
                    "rating" to 4.6,
                    "reviewCount" to 287,
                ),
                hashMapOf(
                    "id" to "5",
                    "name" to "Grand Socco",
                    "descriptionFr" to "Place emblématique reliant médina et ville moderne",
                    "descriptionEn" to "Iconic square linking medina and modern city",
                    "descriptionAr" to "ساحة رمزية تربط المدينة القديمة بالحديثة",
                    "category" to "SHOPPING",
                    "latitude" to 35.7878,
                    "longitude" to -5.8101,
                    "address" to "Grand Socco, Tanger",
                    "photos" to listOf("https://example.com/grand-socco.jpg"),
                    "rating" to 4.5,
                    "reviewCount" to 195,
                ),
                hashMapOf(
                    "id" to "6",
                    "name" to "Plage Malabata",
                    "descriptionFr" to "Longue plage urbaine idéale pour se promener",
                    "descriptionEn" to "Long urban beach, ideal for walks",
                    "descriptionAr" to "شاطئ حضري طويل مثالي للتنزه",
                    "category" to "BEACH",
                    "latitude" to 35.7997,
                    "longitude" to -5.7891,
                    "address" to "Malabata, Tanger",
                    "photos" to listOf("https://example.com/malabata.jpg"),
                    "rating" to 4.4,
                    "reviewCount" to 156,
                ),
            )

            val batch = firestore.batch()
            places.forEach { place ->
                val id = place["id"] as String
                batch.set(placesRef.document(id), place)
            }
            batch.commit().await()
        }.getOrElse {
            // Silent fail: Room remains fallback source for offline-first.
        }
    }

    private companion object {
        const val COLLECTION_PLACES = "places"
    }
}
