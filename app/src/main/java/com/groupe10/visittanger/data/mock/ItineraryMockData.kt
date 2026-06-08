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
            title = mapOf(
                "fr" to "1 jour à Tanger",
                "en" to "1 day in Tangier",
                "ar" to "يوم واحد في طنجة"
            ),
            description = mapOf(
                "fr" to "L'essentiel de Tanger en une journée. Des ruelles de la médina aux falaises du Cap Spartel.",
                "en" to "The essentials of Tangier in one day. From the alleys of the medina to the cliffs of Cape Spartel.",
                "ar" to "أساسيات طنجة في يوم واحد. من أزقة المدينة القديمة إلى منحدرات كاب سبارتيل."
            ),
            duration = mapOf("fr" to "1 jour", "en" to "1 day", "ar" to "يوم واحد"),
            durationHours = 8,
            type = ItineraryType.ONE_DAY,
            coverPhoto = places[3].photos.firstOrNull() ?: "",
            difficulty = mapOf("fr" to "Facile", "en" to "Easy", "ar" to "سهل"),
            totalDistanceKm = 18.5,
            tags = listOf("Culture", "Nature", "Incontournable"),
            places = listOf(
                ItineraryStop(1, places[3], mapOf("fr" to "1h30", "en" to "1h30", "ar" to "ساعة ونصف"), mapOf("fr" to "Commencez tôt pour éviter la foule", "en" to "Start early to avoid the crowds", "ar" to "ابدأ مبكراً لتجنب الزحام"), "09:00"),
                ItineraryStop(2, places[4], mapOf("fr" to "1h", "en" to "1h", "ar" to "ساعة واحدة"), mapOf("fr" to "Goûtez le thé à la menthe", "en" to "Taste the mint tea", "ar" to "تذوق الشاي بالنعناع"), "10:30"),
                ItineraryStop(3, places[0], mapOf("fr" to "2h", "en" to "2h", "ar" to "ساعتان"), mapOf("fr" to "Vue panoramique imprenable", "en" to "Breathtaking panoramic view", "ar" to "إطلالة بانورامية خلابة"), "12:00"),
                ItineraryStop(4, places[6], mapOf("fr" to "1h", "en" to "1h", "ar" to "ساعة واحدة"), mapOf("fr" to "Déjeuner avec vue sur la mer", "en" to "Lunch with a sea view", "ar" to "غداء مع إطلالة على البحر"), "14:00"),
                ItineraryStop(5, places[1], mapOf("fr" to "2h", "en" to "2h", "ar" to "ساعتان"), mapOf("fr" to "Coucher de soleil magique", "en" to "Magical sunset", "ar" to "غروب شمس ساحر"), "16:00")
            )
        ),
        // Itinéraire 2: Week-end complet
        Itinerary(
            id = "itin_2",
            title = mapOf(
                "fr" to "Week-end à Tanger",
                "en" to "Weekend in Tangier",
                "ar" to "عطلة نهاية الأسبوع في طنجة"
            ),
            description = mapOf(
                "fr" to "Deux jours pour explorer Tanger en profondeur. Histoire, nature et gastronomie au programme.",
                "en" to "Two days to explore Tangier in depth. History, nature and gastronomy on the program.",
                "ar" to "يومان لاستكشاف طنجة بعمق. التاريخ والطبيعة وفن الطهو في البرنامج."
            ),
            duration = mapOf("fr" to "2 jours", "en" to "2 days", "ar" to "يومان"),
            durationHours = 16,
            type = ItineraryType.WEEKEND,
            coverPhoto = places[0].photos.firstOrNull() ?: "",
            difficulty = mapOf("fr" to "Modéré", "en" to "Moderate", "ar" to "متوسط"),
            totalDistanceKm = 35.0,
            tags = listOf("Complet", "Histoire", "Nature", "Food"),
            places = listOf(
                ItineraryStop(1, places[3], mapOf("fr" to "2h", "en" to "2h", "ar" to "ساعتان"), mapOf("fr" to "Jour 1 — Médina le matin", "en" to "Day 1 — Medina in the morning", "ar" to "اليوم الأول - المدينة القديمة صباحاً"), "09:00"),
                ItineraryStop(2, places[0], mapOf("fr" to "1h30", "en" to "1h30", "ar" to "ساعة ونصف"), mapOf("fr" to "Jour 1 — Kasbah après-midi", "en" to "Day 1 — Kasbah afternoon", "ar" to "اليوم الأول - القصبة بعد الظهر"), "11:30"),
                ItineraryStop(3, places[4], mapOf("fr" to "1h", "en" to "1h", "ar" to "ساعة واحدة"), mapOf("fr" to "Jour 1 — Grand Socco shopping", "en" to "Day 1 — Grand Socco shopping", "ar" to "اليوم الأول - التسوق في السوق الكبير"), "14:00"),
                ItineraryStop(4, places[6], mapOf("fr" to "1h30", "en" to "1h30", "ar" to "ساعة ونصف"), mapOf("fr" to "Jour 1 — Café Hafa au coucher du soleil", "en" to "Day 1 — Café Hafa at sunset", "ar" to "اليوم الأول - مقهى الحافة عند غروب الشمس"), "17:00"),
                ItineraryStop(5, places[1], mapOf("fr" to "2h", "en" to "2h", "ar" to "ساعتان"), mapOf("fr" to "Jour 2 — Cap Spartel matin", "en" to "Day 2 — Cape Spartel morning", "ar" to "اليوم الثاني - كاب سبارتيل صباحاً"), "09:00"),
                ItineraryStop(6, places[2], mapOf("fr" to "2h", "en" to "2h", "ar" to "ساعتان"), mapOf("fr" to "Jour 2 — Grottes d'Hercule", "en" to "Day 2 — Hercules Caves", "ar" to "اليوم الثاني - مغارات هرقل"), "11:30"),
                ItineraryStop(7, places[5], mapOf("fr" to "2h", "en" to "2h", "ar" to "ساعتان"), mapOf("fr" to "Jour 2 — Plage Malabata après-midi", "en" to "Day 2 — Malabata beach afternoon", "ar" to "اليوم الثاني - شاطئ مالاباطا بعد الظهر"), "15:00")
            )
        ),
        // Itinéraire 3: Tanger historique
        Itinerary(
            id = "itin_3",
            title = mapOf(
                "fr" to "Tanger historique",
                "en" to "Historical Tangier",
                "ar" to "طنجة التاريخية"
            ),
            description = mapOf(
                "fr" to "Plongez dans l'histoire millénaire de Tanger. Médina, Kasbah et monuments incontournables.",
                "en" to "Immerse yourself in the thousand-year-old history of Tangier. Medina, Kasbah and essential monuments.",
                "ar" to "انغمس في تاريخ طنجة الممتد لآلاف السنين. المدينة القديمة والقصبة والمعالم الأساسية."
            ),
            duration = mapOf("fr" to "1 jour", "en" to "1 day", "ar" to "يوم واحد"),
            durationHours = 7,
            type = ItineraryType.HISTORICAL,
            coverPhoto = places[0].photos.firstOrNull() ?: "",
            difficulty = mapOf("fr" to "Facile", "en" to "Easy", "ar" to "سهل"),
            totalDistanceKm = 5.0,
            tags = listOf("Histoire", "Culture", "Architecture"),
            places = listOf(
                ItineraryStop(1, places[3], mapOf("fr" to "2h", "en" to "2h", "ar" to "ساعتان"), mapOf("fr" to "Perdez-vous dans les ruelles", "en" to "Get lost in the alleys", "ar" to "توه في الأزقة"), "09:00"),
                ItineraryStop(2, places[7], mapOf("fr" to "1h", "en" to "1h", "ar" to "ساعة واحدة"), mapOf("fr" to "Place mythique de la médina", "en" to "Mythical place of the medina", "ar" to "ساحة أسطورية في المدينة القديمة"), "11:00"),
                ItineraryStop(3, places[0], mapOf("fr" to "2h", "en" to "2h", "ar" to "ساعتان"), mapOf("fr" to "Musée de la Kasbah inclus", "en" to "Kasbah Museum included", "ar" to "متحف القصبة متضمن"), "12:30"),
                ItineraryStop(4, places[4], mapOf("fr" to "1h30", "en" to "1h30", "ar" to "ساعة ونصف"), mapOf("fr" to "Marché et artisanat local", "en" to "Market and local crafts", "ar" to "السوق والحرف اليدوية المحلية"), "15:00")
            )
        ),
        // Itinéraire 4: Nature et évasion
        Itinerary(
            id = "itin_4",
            title = mapOf(
                "fr" to "Nature et évasion",
                "en" to "Nature and escape",
                "ar" to "الطبيعة والهروب"
            ),
            description = mapOf(
                "fr" to "Les plus beaux espaces naturels autour de Tanger. Falaises, grottes et plages sauvages.",
                "en" to "The most beautiful natural spaces around Tangier. Cliffs, caves and wild beaches.",
                "ar" to "أجمل المساحات الطبيعية حول طنجة. المنحدرات والمغارات والشواطئ البرية."
            ),
            duration = mapOf("fr" to "1 jour", "en" to "1 day", "ar" to "يوم واحد"),
            durationHours = 8,
            type = ItineraryType.NATURE,
            coverPhoto = places[1].photos.firstOrNull() ?: "",
            difficulty = mapOf("fr" to "Modéré", "en" to "Moderate", "ar" to "متوسط"),
            totalDistanceKm = 40.0,
            tags = listOf("Nature", "Plages", "Randonnée"),
            places = listOf(
                ItineraryStop(1, places[1], mapOf("fr" to "2h", "en" to "2h", "ar" to "ساعتان"), mapOf("fr" to "Arrivez tôt pour voir le lever de soleil", "en" to "Arrive early to see the sunrise", "ar" to "الوصول مبكراً لمشاهدة شروق الشمس"), "08:00"),
                ItineraryStop(2, places[2], mapOf("fr" to "2h", "en" to "2h", "ar" to "ساعتان"), mapOf("fr" to "Explorez les deux côtés des grottes", "en" to "Explore both sides of the caves", "ar" to "استكشف جانبي المغارات"), "10:30"),
                ItineraryStop(3, places[5], mapOf("fr" to "3h", "en" to "3h", "ar" to "3 ساعات"), mapOf("fr" to "Baignade et détente", "en" to "Swimming and relaxation", "ar" to "السباحة والاسترخاء"), "13:30")
            )
        ),
        // Itinéraire 5: Gastronomie tangéroise
        Itinerary(
            id = "itin_5",
            title = mapOf(
                "fr" to "Saveurs de Tanger",
                "en" to "Flavors of Tangier",
                "ar" to "نكهات طنجة"
            ),
            description = mapOf(
                "fr" to "Un tour gourmand de Tanger. Thé à la menthe, tagines et spécialités locales.",
                "en" to "A gourmet tour of Tangier. Mint tea, tagines and local specialties.",
                "ar" to "جولة تذوق في طنجة. شاي بالنعناع وطواجين وتخصصات محلية."
            ),
            duration = mapOf("fr" to "1 jour", "en" to "1 day", "ar" to "يوم واحد"),
            durationHours = 6,
            type = ItineraryType.GASTRONOMY,
            coverPhoto = places[6].photos.firstOrNull() ?: "",
            difficulty = mapOf("fr" to "Facile", "en" to "Easy", "ar" to "سهل"),
            totalDistanceKm = 8.0,
            tags = listOf("Food", "Culture", "Marché"),
            places = listOf(
                ItineraryStop(1, places[4], mapOf("fr" to "1h30", "en" to "1h30", "ar" to "ساعة ونصف"), mapOf("fr" to "Marché du matin, fruits et épices frais", "en" to "Morning market, fresh fruits and spices", "ar" to "سوق الصباح، فواكه وتوابل طازجة"), "09:00"),
                ItineraryStop(2, places[7], mapOf("fr" to "1h", "en" to "1h", "ar" to "ساعة واحدة"), mapOf("fr" to "Café traditionnel au Petit Socco", "en" to "Traditional café at Petit Socco", "ar" to "مقهى تقليدي في السوق الصغير"), "11:00"),
                ItineraryStop(3, places[6], mapOf("fr" to "2h", "en" to "2h", "ar" to "ساعتان"), mapOf("fr" to "Déjeuner légendaire avec vue mer", "en" to "Legendary lunch with sea view", "ar" to "غداء أسطوري مع إطلالة على البحر"), "13:00"),
                ItineraryStop(4, places[3], mapOf("fr" to "1h30", "en" to "1h30", "ar" to "ساعة ونصف"), mapOf("fr" to "Pâtisseries et thé dans la médina", "en" to "Pastries and tea in the medina", "ar" to "حلويات وشاي في المدينة القديمة"), "16:00")
            )
        )
    )

    fun getItineraryById(id: String): Itinerary? =
        itineraries.find { it.id == id }

    fun getItinerariesByType(type: ItineraryType): List<Itinerary> =
        itineraries.filter { it.type == type }
}
