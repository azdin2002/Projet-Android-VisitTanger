package com.groupe10.visittanger.domain.model

enum class Category(val labelFr: String, val labelEn: String, val labelAr: String) {
    HISTORY("Histoire", "History", "تاريخ"),
    NATURE("Nature", "Nature", "طبيعة"),
    FOOD("Gastronomie", "Food", "مطاعم"),
    SHOPPING("Shopping", "Shopping", "تسوق"),
    BEACH("Plages", "Beaches", "شواطئ"),
    EVENTS("Événements", "Events", "فعاليات")
}
