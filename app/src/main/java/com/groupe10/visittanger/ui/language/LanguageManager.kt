package com.groupe10.visittanger.ui.language

import android.content.Context
import android.content.res.Configuration
import androidx.compose.ui.unit.LayoutDirection
import java.util.Locale

object LanguageManager {

    /**
     * Applique la locale au Context.
     * À appeler dans MainActivity.attachBaseContext().
     */
    fun applyLanguage(context: Context, langCode: String): Context {
        val locale = when (langCode) {
            "ar" -> Locale("ar")
            "en" -> Locale("en")
            else -> Locale("fr")
        }
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    /** LayoutDirection pour CompositionLocalProvider. */
    fun getLayoutDirection(langCode: String): LayoutDirection =
        if (langCode == "ar") LayoutDirection.Rtl
        else LayoutDirection.Ltr

    /** Nom natif affiché dans le sélecteur. */
    fun getLanguageNativeName(langCode: String): String = when (langCode) {
        "ar" -> "العربية"
        "en" -> "English"
        else -> "Français"
    }

    /** Flag emoji affiché à côté du nom. */
    fun getLanguageFlag(langCode: String): String = when (langCode) {
        "ar" -> "🇲🇦"
        "en" -> "🇬🇧"
        else -> "🇫🇷"
    }
}
