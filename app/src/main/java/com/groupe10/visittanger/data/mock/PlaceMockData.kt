package com.groupe10.visittanger.data.mock

import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place

object PlaceMockData {
    val places = listOf(
        Place(
            id = "1",
            name = "Kasbah de Tanger",
            description = mapOf(
                "fr" to "Ancienne citadelle dominant la baie de Tanger, la Kasbah offre une vue panoramique exceptionnelle et abrite le musée des Arts marocains.",
                "en" to "Ancient citadel overlooking the bay of Tangier, the Kasbah offers an exceptional panoramic view and houses the Museum of Moroccan Arts.",
                "ar" to "قصبة قديمة تطل على خليج طنجة، توفر إطلالة بانورامية استثنائية وتضم متحف الفنون المغربية."
            ),
            category = Category.HISTORY,
            latitude = 35.7907, longitude = -5.8144,
            address = "Kasbah, Tanger",
            photos = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2e/Tanger_Kasbah.jpg/1200px-Tanger_Kasbah.jpg"),
            rating = 4.8f, reviewCount = 342, distanceKm = 1.2
        ),
        Place(
            id = "2",
            name = "Cap Spartel",
            description = mapOf(
                "fr" to "Le cap où se rencontrent l'Atlantique et la Méditerranée, célèbre pour son phare historique.",
                "en" to "The cape where the Atlantic and the Mediterranean meet, famous for its historic lighthouse.",
                "ar" to "الرأس حيث يلتقي المحيط الأطلسي والبحر الأبيض المتوسط، يشتهر بمنارته التاريخية."
            ),
            category = Category.NATURE,
            latitude = 35.7863, longitude = -5.9248,
            address = "Cap Spartel, Tanger",
            photos = listOf("https://upload.wikimedia.org/wikipedia/commons/6/6e/Cap_Spartel.jpg"),
            rating = 4.9f, reviewCount = 521, distanceKm = 14.0
        ),
        Place(
            id = "3",
            name = "Grottes d'Hercule",
            description = mapOf(
                "fr" to "Grottes mythiques creusées dans la falaise, dont l'ouverture vers la mer évoque la carte de l'Afrique.",
                "en" to "Mythical caves carved into the cliff, whose opening to the sea resembles the map of Africa.",
                "ar" to "كهوف أسطورية محفورة في الجرف، فتحتها على البحر تشبه خريطة أفريقيا."
            ),
            category = Category.NATURE,
            latitude = 35.7707, longitude = -5.9215,
            address = "Cap Spartel, Tanger",
            photos = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/8/8c/Grotte_d%27hercule.jpg/1200px-Grotte_d%27hercule.jpg"),
            rating = 4.7f, reviewCount = 418, distanceKm = 16.0
        ),
        Place(
            id = "4",
            name = "Médina de Tanger",
            description = mapOf(
                "fr" to "Le coeur historique de Tanger, un labyrinthe de ruelles colorées et de marchés animés.",
                "en" to "The historical heart of Tangier, a labyrinth of colorful alleys and lively markets.",
                "ar" to "القلب التاريخي لطنجة، متاهة من الأزقة الملونة والأسواق النابضة بالحياة."
            ),
            category = Category.HISTORY,
            latitude = 35.7896, longitude = -5.8137,
            address = "Médina, Tanger",
            photos = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3e/Medina_of_Tangier.jpg/1200px-Medina_of_Tangier.jpg"),
            rating = 4.6f, reviewCount = 287, distanceKm = 0.8
        ),
        Place(
            id = "5",
            name = "Grand Socco",
            description = mapOf(
                "fr" to "La grande place animée de Tanger, point de rencontre entre la ville moderne et la médina.",
                "en" to "The large, bustling square of Tangier, a meeting point between the modern city and the medina.",
                "ar" to "الساحة الكبيرة والنابضة بالحياة في طنجة، وهي نقطة التقاء بين المدينة الحديثة والمدينة القديمة."
            ),
            category = Category.SHOPPING,
            latitude = 35.7878, longitude = -5.8101,
            address = "Place du Grand Socco, Tanger",
            photos = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/1/1e/Grand_Socco_Tangier.jpg/1200px-Grand_Socco_Tangier.jpg"),
            rating = 4.5f, reviewCount = 195, distanceKm = 0.5
        ),
        Place(
            id = "6",
            name = "Plage Malabata",
            description = mapOf(
                "fr" to "Belle plage à l'est de Tanger offrant une vue magnifique sur la baie et la ville.",
                "en" to "Beautiful beach east of Tangier offering a magnificent view of the bay and the city.",
                "ar" to "شاطئ جميل شرق طنجة يوفر إطلالة رائعة على الخليج والمدينة."
            ),
            category = Category.BEACH,
            latitude = 35.7997, longitude = -5.7891,
            address = "Malabata, Tanger",
            photos = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Malabata_beach.jpg/1200px-Malabata_beach.jpg"),
            rating = 4.4f, reviewCount = 156, distanceKm = 5.0
        ),
        Place(
            id = "7",
            name = "Café Hafa",
            description = mapOf(
                "fr" to "Café mythique fondé en 1921, situé sur les falaises dominant le détroit de Gibraltar.",
                "en" to "Mythic café founded in 1921, located on the cliffs overlooking the Strait of Gibraltar.",
                "ar" to "مقهى أسطوري تأسس عام 1921، يقع على المنحدرات المطلة على مضيق جبل طارق."
            ),
            category = Category.FOOD,
            latitude = 35.8012, longitude = -5.8289,
            address = "Marshan, Tanger",
            photos = listOf(),
            rating = 4.7f, reviewCount = 389, distanceKm = 2.1
        ),
        Place(
            id = "8",
            name = "Petit Socco",
            description = mapOf(
                "fr" to "Place historique au coeur de la médina, autrefois centre de la vie diplomatique de Tanger.",
                "en" to "Historic square in the heart of the medina, formerly the center of diplomatic life in Tangier.",
                "ar" to "ساحة تاريخية في قلب المدينة القديمة، كانت سابقاً مركزاً للحياة الدبلوماسية في طنجة."
            ),
            category = Category.EVENTS,
            latitude = 35.7889, longitude = -5.8127,
            address = "Médina, Tanger",
            photos = listOf(),
            rating = 4.5f, reviewCount = 223, distanceKm = 0.9
        )
    )
}
