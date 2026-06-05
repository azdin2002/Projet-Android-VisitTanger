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
    /**
     * Seeds or updates the places collection with rich English data.
     * We ignore the "isEmpty" check to ensure the new schema (teaser, localTips) is applied.
     */
    suspend fun seedPlacesIfEmpty() {
        runCatching {
            val placesRef = firestore.collection("places")
            
            Log.d("FirestoreSeeder", "Forcing update of collection with rich data...")

            val places = listOf(
                hashMapOf(
                    "id" to "1", 
                    "name" to "Kasbah de Tanger",
                    "teaserEn" to "A majestic 15th-century fortress offering panoramic views of the Mediterranean.",
                    "descriptionEn" to "Perched high above the city, the Kasbah is the historical beating heart of Tangier. Its intricate walls and serene zellige-tiled courtyards reflect centuries of Northern Moroccan heritage, with the current Dar al-Makhzen palace structures dating back to 1737. It is a quiet, labyrinthine sanctuary away from the bustle below.",
                    "localTipEn" to "Enter through Bab al-Bahar (Gate of the Sea) for the most striking, elevated view of the Strait of Gibraltar.",
                    "category" to "HISTORY", "latitude" to 35.7907, "longitude" to -5.8144, 
                    "address" to "Place de la Kasbah, Tanger", "photos" to emptyList<String>(), 
                    "rating" to 4.8, "reviewCount" to 342
                ),
                hashMapOf(
                    "id" to "2", 
                    "name" to "Cap Spartel",
                    "teaserEn" to "The dramatic edge of Africa where the Atlantic Ocean meets the Mediterranean Sea.",
                    "descriptionEn" to "Standing 315 meters above sea level, this promontory is guarded by a striking Moorish-style lighthouse built in 1864 by international commission. Surrounded by a lush 300-hectare reserve of cork oaks and pine trees, it serves as a critical bottleneck for spectacular seasonal bird migrations.",
                    "localTipEn" to "Visit at sunset to watch the yellow-ochre, minaret-style lighthouse glow against the fading ocean light.",
                    "category" to "NATURE", "latitude" to 35.7863, "longitude" to -5.9248, 
                    "address" to "Cap Spartel, Tanger", "photos" to emptyList<String>(), 
                    "rating" to 4.9, "reviewCount" to 521
                ),
                hashMapOf(
                    "id" to "3", 
                    "name" to "Grottes d Hercule",
                    "teaserEn" to "A mythical limestone cave system framing the sea in the shape of the African continent.",
                    "descriptionEn" to "Steeped in Greek mythology, these caves are where Hercules supposedly rested before his 11th labor. The interior is a fascinating hybrid of natural erosion and human engineering, where indigenous populations spent centuries cutting circular stone wheels from the rock walls to use as millstones.",
                    "localTipEn" to "The famous \"Map of Africa\" opening is perfectly aligned for dramatic silhouettes during golden hour.",
                    "category" to "NATURE", "latitude" to 35.7707, "longitude" to -5.9215, 
                    "address" to "Route des Grottes, Tanger", "photos" to emptyList<String>(), 
                    "rating" to 4.7, "reviewCount" to 418
                ),
                hashMapOf(
                    "id" to "4", 
                    "name" to "Medina de Tanger",
                    "teaserEn" to "A vibrant, labyrinthine old city filled with colorful doors, history, and daily life.",
                    "descriptionEn" to "The Ancien Medina is a sensory journey through Tangier’s past. Its steep, winding alleys are lined with traditional riads, artisan workshops, and walls painted in bright whites and ocean blues. It remains a living, breathing neighborhood where the region's cultural heritage is actively preserved.",
                    "localTipEn" to "Ditch the map and let yourself get lost; the most authentic artisan shops are tucked away in the narrowest residential streets.",
                    "category" to "HISTORY", "latitude" to 35.7896, "longitude" to -5.8137, 
                    "address" to "Médina, Tanger", "photos" to emptyList<String>(), 
                    "rating" to 4.6, "reviewCount" to 287
                ),
                hashMapOf(
                    "id" to "5", 
                    "name" to "Grand Socco",
                    "teaserEn" to "The iconic palm-lined square bridging Tangier’s modern city and the historic Medina.",
                    "descriptionEn" to "Officially named Place du 9 Avril 1947, this bustling plaza is the historic crossroads of Tangier. Framed by the lush Mendoubia Gardens and the cinematic minaret of the Sidi Bou Abib Mosque, it perfectly captures the city's transition from the ancient to the modern world.",
                    "localTipEn" to "Grab a mint tea at a perimeter café and listen to the beautiful chaos of the central fountain and market vendors.",
                    "category" to "SHOPPING", "latitude" to 35.7878, "longitude" to -5.8101, 
                    "address" to "Place du 9 Avril 1947, Tanger", "photos" to emptyList<String>(), 
                    "rating" to 4.5, "reviewCount" to 195
                ),
                hashMapOf(
                    "id" to "6", 
                    "name" to "Plage Malabata",
                    "teaserEn" to "A sweeping sandy beach offering the ultimate view of Tangier’s modern skyline.",
                    "descriptionEn" to "Curving along the eastern bay of the city, Malabata provides a more relaxed coastal experience. The calm Mediterranean waters contrast beautifully with the dynamic backdrop of Tangier's high-rises and the distant mountains.",
                    "localTipEn" to "The coastal corniche here is wide and well-paved, making it perfect for an early morning run or a peaceful evening walk.",
                    "category" to "BEACH", "latitude" to 35.7997, "longitude" to -5.7891, 
                    "address" to "Corniche Malabata, Tanger", "photos" to emptyList<String>(), 
                    "rating" to 4.4, "reviewCount" to 156
                ),
                hashMapOf(
                    "id" to "7", 
                    "name" to "Stade Ibn Battouta",
                    "teaserEn" to "A monumental, state-of-the-art sports arena and the pride of Northern Moroccan football.",
                    "descriptionEn" to "Named after the legendary Moroccan explorer, this architectural masterpiece has recently been expanded to a capacity of over 75,000 in preparation for the 2030 World Cup. Its massive structure and world-class acoustics make it a formidable fortress for IR Tanger and the national team.",
                    "localTipEn" to "Catch a local match to experience the unrivaled, high-energy passion of Tangier’s football supporters.",
                    "category" to "EVENTS", "latitude" to 35.7522, "longitude" to -5.8358, 
                    "address" to "Route de Rabat, Tanger", "photos" to emptyList<String>(), 
                    "rating" to 4.6, "reviewCount" to 312
                ),
                hashMapOf(
                    "id" to "8", 
                    "name" to "Musée de la Kasbah",
                    "teaserEn" to "A treasure trove of Mediterranean cultures housed in a former Sultan’s palace.",
                    "descriptionEn" to "Located inside the Dar al-Makhzen, this museum preserves the deep history of the northern region. From Roman mosaics discovered at Volubilis to intricately carved wooden ceilings and a breathtaking Andalusian garden, it provides profound insight into Moroccan antiquity.",
                    "localTipEn" to "Take your time in the central courtyard; the marble fountain and remarkably preserved stucco work are masterpieces of traditional craftsmanship.",
                    "category" to "HISTORY", "latitude" to 35.7912, "longitude" to -5.8150, 
                    "address" to "Place de la Kasbah, Tanger", "photos" to emptyList<String>(), 
                    "rating" to 4.5, "reviewCount" to 198
                ),
                hashMapOf(
                    "id" to "9", 
                    "name" to "Plage de Tanger",
                    "teaserEn" to "The energetic main beach stretching along the city’s lively central corniche.",
                    "descriptionEn" to "Tangier’s main beach is the ultimate urban coastal playground. The wide, recently revamped promenade is lined with modern cafes, while the golden sands host impromptu football matches and families enjoying the sea breeze right in the city center.",
                    "localTipEn" to "Rent a sunbed during the day, and stick around for the vibrant street life and performances along the corniche after dark.",
                    "category" to "BEACH", "latitude" to 35.7956, "longitude" to -5.8089, 
                    "address" to "Corniche, Tanger", "photos" to emptyList<String>(), 
                    "rating" to 4.3, "reviewCount" to 445
                ),
                hashMapOf(
                    "id" to "10", 
                    "name" to "Souk de Tanger",
                    "teaserEn" to "A bustling marketplace overflowing with spices, textiles, and traditional crafts.",
                    "descriptionEn" to "The souks radiating from the Petit Socco are a testament to Tangier's rich trading history. From hand-woven mountain carpets to vivid displays of cumin and paprika, the markets offer an authentic glimpse into local commerce and artisanship.",
                    "localTipEn" to "Practice your bargaining skills with a smile, especially when shopping for handmade leather goods or local ceramics.",
                    "category" to "SHOPPING", "latitude" to 35.7889, "longitude" to -5.8125, 
                    "address" to "Médina, Tanger", "photos" to emptyList<String>(), 
                    "rating" to 4.4, "reviewCount" to 267
                ),
                hashMapOf(
                    "id" to "11", 
                    "name" to "Restaurant El Korsan",
                    "teaserEn" to "An elegant dining experience serving authentic Moroccan cuisine with historic charm.",
                    "descriptionEn" to "Located within the legendary El Minzah Hotel, El Korsan surrounds guests with exquisite Andalusian décor. It offers a refined culinary journey through Moroccan flavors, featuring intricately spiced tagines and pastillas, often accompanied by traditional live music.",
                    "localTipEn" to "The seafood tagine is an essential order for a truly classic Northern Moroccan feast.",
                    "category" to "FOOD", "latitude" to 35.7934, "longitude" to -5.8167, 
                    "address" to "Rue de la Kasbah, Tanger", "photos" to emptyList<String>(), 
                    "rating" to 4.7, "reviewCount" to 189
                ),
                hashMapOf(
                    "id" to "12", 
                    "name" to "Parc Perdicaris",
                    "teaserEn" to "A sprawling, romantic forest sanctuary overlooking the Strait of Gibraltar.",
                    "descriptionEn" to "Created in the late 1910s by Greek-American expatriate Ion Perdicaris, this 70-hectare botanical park is a tranquil escape from the city. Its shaded trails wind through eucalyptus, cork oaks, and mimosas, offering breathtaking, elevated views of the ocean.",
                    "localTipEn" to "Follow the main trail up the panoramic hill for the most peaceful ocean vistas in the entire city.",
                    "category" to "NATURE", "latitude" to 35.7650, "longitude" to -5.8820, 
                    "address" to "Route de la Montagne, Tanger", "photos" to emptyList<String>(), 
                    "rating" to 4.5, "reviewCount" to 134
                )
            )

            val batch = firestore.batch()
            places.forEach { place ->
                val ref = placesRef.document(place["id"] as String)
                batch.set(ref, place)
            }
            batch.commit().await()
            Log.d("FirestoreSeeder", "Data forced successfully with English rich content.")
        }.onFailure { e ->
            Log.e("FirestoreSeeder", "Erreur: ${e.message}")
        }
    }
}
