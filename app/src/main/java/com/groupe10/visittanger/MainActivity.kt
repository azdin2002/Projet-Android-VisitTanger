package com.groupe10.visittanger

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.groupe10.visittanger.data.datastore.UserPreferencesDataStore
import com.groupe10.visittanger.data.remote.FirestoreSeeder
import com.groupe10.visittanger.ui.language.LanguageManager
import com.groupe10.visittanger.ui.language.LanguageViewModel
import com.groupe10.visittanger.ui.navigation.MainScreen
import com.groupe10.visittanger.ui.theme.VisitTangerTheme
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@EntryPoint
@InstallIn(SingletonComponent::class)
interface UserPreferencesEntryPoint {
    fun userPreferencesDataStore(): UserPreferencesDataStore
}

private const val TAG_THEME = "VisitTanger.Theme"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferencesDataStore: UserPreferencesDataStore

    @Inject
    lateinit var firestoreSeeder: FirestoreSeeder

    /**
     * Applique la langue AVANT que le layout soit gonflé.
     * runBlocking ici est intentionnel et accepté
     * (seul endroit autorisé dans tout le projet).
     */
    override fun attachBaseContext(newBase: Context) {
        // On récupère le DataStore via un EntryPoint car l'injection Hilt 
        // n'est pas encore faite dans attachBaseContext
        val entryPoint = EntryPointAccessors.fromApplication(
            newBase.applicationContext ?: newBase,
            UserPreferencesEntryPoint::class.java
        )
        val dataStore = entryPoint.userPreferencesDataStore()

        val langCode = runBlocking {
            dataStore.language.first()
        }
        super.attachBaseContext(
            LanguageManager.applyLanguage(newBase, langCode)
        )
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            firestoreSeeder.seedPlacesIfEmpty()
        }

        setContent {
            val languageViewModel: LanguageViewModel = hiltViewModel()
            val currentLang by languageViewModel.currentLanguage
                .collectAsStateWithLifecycle()

            val lifecycleOwner = LocalLifecycleOwner.current
            val isDarkMode by userPreferencesDataStore.isDarkMode
                .collectAsStateWithLifecycle(
                    initialValue = false,
                    lifecycle = lifecycleOwner.lifecycle,
                )

            LaunchedEffect(isDarkMode) {
                Log.d(TAG_THEME, "VisitTangerTheme darkTheme=$isDarkMode (DataStore)")
            }

            val windowSizeClass = calculateWindowSizeClass(this)

            VisitTangerTheme(darkTheme = isDarkMode) {
                CompositionLocalProvider(
                    LocalLayoutDirection provides
                        LanguageManager.getLayoutDirection(currentLang)
                ) {
                    MainScreen(windowSizeClass = windowSizeClass)
                }
            }
        }
    }
}
