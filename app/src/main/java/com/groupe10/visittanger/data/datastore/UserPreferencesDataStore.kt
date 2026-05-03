package com.groupe10.visittanger.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    // ── Clés ──────────────────────────────────────────────
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val LANGUAGE_KEY  = stringPreferencesKey("language")

    // ── Dark mode ─────────────────────────────────────────
    val isDarkMode: Flow<Boolean> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { it[DARK_MODE_KEY] ?: false }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { it[DARK_MODE_KEY] = enabled }
    }

    // ── Langue ────────────────────────────────────────────
    val language: Flow<String> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { it[LANGUAGE_KEY] ?: "fr" }

    suspend fun setLanguage(langCode: String) {
        dataStore.edit { it[LANGUAGE_KEY] = langCode }
    }
}
