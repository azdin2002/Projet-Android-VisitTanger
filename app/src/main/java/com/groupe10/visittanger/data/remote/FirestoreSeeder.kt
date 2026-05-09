package com.groupe10.visittanger.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreSeeder @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun seedPlacesIfEmpty() {
        runCatching {
            val placesRef = firestore.collection("places")
            val existing = placesRef.limit(1).get().await()
            if (!existing.isEmpty) {
                Log.d("FirestoreSeeder", "Données déjà présentes, skip")
                return
            }

            Log.d("FirestoreSeeder", "Collection vide, insertion...")

            val places = listOf(
                hashMapOf("id" to "1", "name" to "Kasbah de Tanger",
                    "descriptionFr" to "Ancienne citadelle dominant la médina",
                    "descriptionEn" to "Ancient citadel overlooking the medina",
                    "descriptionAr" to "قلعة قديمة تطل على المدينة القديمة",
                    "category" to "HISTORY", "latitude" to 35.7907,
                    "longitude" to -5.8144, "address" to "Kasbah, Tanger",
                    "photos" to emptyList<String>(), "rating" to 4.8, "reviewCount" to 342),
                hashMapOf("id" to "2", "name" to "Cap Spartel",
                    "descriptionFr" to "Pointe nord-ouest de l Afrique",
                    "descriptionEn" to "Northwestern tip of Africa",
                    "descriptionAr" to "الطرف الشمالي الغربي لأفريقيا",
                    "category" to "NATURE", "latitude" to 35.7863,
                    "longitude" to -5.9248, "address" to "Cap Spartel, Tanger",
                    "photos" to emptyList<String>(), "rating" to 4.9, "reviewCount" to 521),
                hashMapOf("id" to "3", "name" to "Grottes d Hercule",
                    "descriptionFr" to "Grottes naturelles spectaculaires",
                    "descriptionEn" to "Spectacular natural caves",
                    "descriptionAr" to "كهوف طبيعية مذهلة",
                    "category" to "NATURE", "latitude" to 35.7707,
                    "longitude" to -5.9215, "address" to "Grottes d Hercule, Tanger",
                    "photos" to emptyList<String>(), "rating" to 4.7, "reviewCount" to 418),
                hashMapOf("id" to "4", "name" to "Medina de Tanger",
                    "descriptionFr" to "Vieille ville historique animée",
                    "descriptionEn" to "Lively historic old town",
                    "descriptionAr" to "المدينة القديمة التاريخية النابضة بالحياة",
                    "category" to "HISTORY", "latitude" to 35.7896,
                    "longitude" to -5.8137, "address" to "Medina, Tanger",
                    "photos" to emptyList<String>(), "rating" to 4.6, "reviewCount" to 287),
                hashMapOf("id" to "5", "name" to "Grand Socco",
                    "descriptionFr" to "Grande place centrale de Tanger",
                    "descriptionEn" to "Main central square of Tangier",
                    "descriptionAr" to "الساحة المركزية الكبرى في طنجة",
                    "category" to "SHOPPING", "latitude" to 35.7878,
                    "longitude" to -5.8101, "address" to "Grand Socco, Tanger",
                    "photos" to emptyList<String>(), "rating" to 4.5, "reviewCount" to 195),
                hashMapOf("id" to "6", "name" to "Plage Malabata",
                    "descriptionFr" to "Belle plage à l est de Tanger",
                    "descriptionEn" to "Beautiful beach east of Tangier",
                    "descriptionAr" to "شاطئ جميل شرق طنجة",
                    "category" to "BEACH", "latitude" to 35.7997,
                    "longitude" to -5.7891, "address" to "Malabata, Tanger",
                    "photos" to emptyList<String>(), "rating" to 4.4, "reviewCount" to 156),

                // NOUVEAU — Stade Ibn Battouta
                hashMapOf("id" to "7", "name" to "Stade Ibn Battouta",
                    "descriptionFr" to "Grand stade de football de Tanger, capacité 45 000 places",
                    "descriptionEn" to "Major football stadium in Tangier, capacity 45,000",
                    "descriptionAr" to "ملعب كرة القدم الكبير في طنجة، سعة 45000 مقعد",
                    "category" to "EVENTS", "latitude" to 35.7522,
                    "longitude" to -5.8358, "address" to "Route de Rabat, Tanger",
                    "photos" to emptyList<String>(), "rating" to 4.6, "reviewCount" to 312),

                // Lieux supplémentaires
                hashMapOf("id" to "8", "name" to "Musée de la Kasbah",
                    "descriptionFr" to "Musée des arts et antiquités marocaines",
                    "descriptionEn" to "Museum of Moroccan arts and antiquities",
                    "descriptionAr" to "متحف الفنون والآثار المغربية",
                    "category" to "HISTORY", "latitude" to 35.7912,
                    "longitude" to -5.8150, "address" to "Kasbah, Tanger",
                    "photos" to emptyList<String>(), "rating" to 4.5, "reviewCount" to 198),
                hashMapOf("id" to "9", "name" to "Plage de Tanger",
                    "descriptionFr" to "Grande plage urbaine en plein centre ville",
                    "descriptionEn" to "Large urban beach in the city center",
                    "descriptionAr" to "شاطئ حضري كبير في وسط المدينة",
                    "category" to "BEACH", "latitude" to 35.7956,
                    "longitude" to -5.8089, "address" to "Corniche, Tanger",
                    "photos" to emptyList<String>(), "rating" to 4.3, "reviewCount" to 445),
                hashMapOf("id" to "10", "name" to "Souk de Tanger",
                    "descriptionFr" to "Marché traditionnel au coeur de la médina",
                    "descriptionEn" to "Traditional market in the heart of the medina",
                    "descriptionAr" to "سوق تقليدي في قلب المدينة العتيقة",
                    "category" to "SHOPPING", "latitude" to 35.7889,
                    "longitude" to -5.8125, "address" to "Médina, Tanger",
                    "photos" to emptyList<String>(), "rating" to 4.4, "reviewCount" to 267),
                hashMapOf("id" to "11", "name" to "Restaurant El Korsan",
                    "descriptionFr" to "Restaurant traditionnel marocain vue sur mer",
                    "descriptionEn" to "Traditional Moroccan restaurant with sea view",
                    "descriptionAr" to "مطعم مغربي تقليدي مطل على البحر",
                    "category" to "FOOD", "latitude" to 35.7934,
                    "longitude" to -5.8167, "address" to "Rue de la Kasbah, Tanger",
                    "photos" to emptyList<String>(), "rating" to 4.7, "reviewCount" to 189),
                hashMapOf("id" to "12", "name" to "Parc Perdicaris",
                    "descriptionFr" to "Parc naturel forestier en bordure de ville",
                    "descriptionEn" to "Natural forest park on the edge of the city",
                    "descriptionAr" to "حديقة غابية طبيعية على أطراف المدينة",
                    "category" to "NATURE", "latitude" to 35.7650,
                    "longitude" to -5.8820, "address" to "Ain Dalia, Tanger",
                    "photos" to emptyList<String>(), "rating" to 4.5, "reviewCount" to 134)
            )

            val batch = firestore.batch()
            places.forEach { place ->
                val ref = placesRef.document(place["id"] as String)
                batch.set(ref, place)
            }
            batch.commit().await()
            Log.d("FirestoreSeeder", "6 lieux insérés avec succès")
        }.onFailure { e ->
            Log.e("FirestoreSeeder", "Erreur: ${e.message}")
        }
    }
}