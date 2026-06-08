package com.groupe10.visittanger.domain.util

fun Map<String, String>.localized(lang: String): String {
    val fallbackOrder = listOf(lang, "fr", "en", "ar")
    for (code in fallbackOrder) {
        this[code]?.takeIf { it.isNotBlank() }?.let { return it }
    }
    return values.firstOrNull { it.isNotBlank() } ?: ""
}
