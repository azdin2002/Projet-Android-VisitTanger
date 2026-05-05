# AI_AGENT.md — Contexte projet pour agent IA

> Ce fichier est destiné à être fourni à un agent IA (Claude, Copilot, Cursor, etc.)
> pour qu'il comprenne le projet et puisse aider efficacement au développement.
> **Toujours joindre ce fichier au début de chaque session de développement.**

---

## Identité du projet

- **Nom** : Visit Tanger
- **Type** : Application mobile Android native
- **Sujet** : Application touristique pour découvrir Tanger (Maroc)
- **Package** : `com.groupe10.visittanger`
- **Repository** : `Projet-Android-VisitTanger`
- **Équipe** : Wail CHAIRI MAHJOR (UI) + Azzeddine SALMOUN (Data)
- **Niveau** : Débutants Android / Kotlin
- **Contexte** : Projet académique Master DevOps & Cloud Computing

---

## Stack technique EXACTE à respecter

```
Langage        : Kotlin (pas Java)
UI             : Jetpack Compose + Material 3 (pas XML layouts)
Architecture   : MVVM strict (pas MVC, pas MVP)
Navigation     : Navigation Compose
State          : StateFlow + collectAsStateWithLifecycle()
DI             : Hilt (pas Koin, pas manual DI)
Base de données: Room (pas SQLite direct)
Réseau         : Retrofit + OkHttp + Gson
Images         : Coil (pas Glide, pas Picasso)
Carte          : Google Maps Compose (maps-compose)
Auth           : Firebase Authentication
Préférences    : DataStore (pas SharedPreferences pour les données utilisateur)
Coroutines     : Kotlin Coroutines + Flow
```

---

## Structure des packages (NE PAS modifier)

```
com.groupe10.visittanger/
├── data/
│   ├── remote/
│   │   ├── api/
│   │   │   ├── PlaceApiService.kt
│   │   │   ├── AuthApiService.kt
│   │   │   └── ItineraryApiService.kt
│   │   └── dto/
│   │       ├── PlaceDto.kt
│   │       ├── CategoryDto.kt
│   │       └── ItineraryDto.kt
│   ├── local/
│   │   ├── dao/
│   │   │   ├── PlaceDao.kt
│   │   │   └── FavoriteDao.kt
│   │   ├── entity/
│   │   │   ├── PlaceEntity.kt
│   │   │   └── FavoriteEntity.kt
│   │   └── AppDatabase.kt
│   ├── repository/
│   │   ├── PlaceRepositoryImpl.kt
│   │   ├── AuthRepositoryImpl.kt
│   │   └── ItineraryRepositoryImpl.kt
│   └── datastore/
│       └── UserPreferencesDataStore.kt
│
├── domain/
│   ├── model/
│   │   ├── Place.kt
│   │   ├── Category.kt
│   │   ├── Itinerary.kt
│   │   ├── User.kt
│   │   └── Review.kt
│   ├── repository/
│   │   ├── PlaceRepository.kt
│   │   ├── AuthRepository.kt
│   │   └── ItineraryRepository.kt
│   └── usecase/
│       ├── GetPlacesUseCase.kt
│       ├── GetPlaceByIdUseCase.kt
│       ├── SearchPlacesUseCase.kt
│       ├── GetPlacesByCategoryUseCase.kt
│       ├── SaveFavoriteUseCase.kt
│       ├── RemoveFavoriteUseCase.kt
│       ├── GetFavoritesUseCase.kt
│       ├── GetItinerariesUseCase.kt
│       ├── LoginUseCase.kt
│       ├── RegisterUseCase.kt
│       └── LogoutUseCase.kt
│
├── ui/
│   ├── auth/
│   │   ├── LoginScreen.kt
│   │   ├── RegisterScreen.kt
│   │   └── AuthViewModel.kt
│   ├── home/
│   │   ├── HomeScreen.kt
│   │   └── HomeViewModel.kt
│   ├── map/
│   │   ├── MapScreen.kt
│   │   └── MapViewModel.kt
│   ├── detail/
│   │   ├── DetailScreen.kt
│   │   └── PlaceViewModel.kt
│   ├── itinerary/
│   │   ├── ItineraryScreen.kt
│   │   ├── ItineraryDetailScreen.kt
│   │   └── ItineraryViewModel.kt
│   ├── favorites/
│   │   ├── FavoritesScreen.kt
│   │   └── FavoritesViewModel.kt
│   ├── profile/
│   │   ├── ProfileScreen.kt
│   │   └── ProfileViewModel.kt
│   ├── navigation/
│   │   ├── AppNavGraph.kt
│   │   └── Screen.kt
│   └── theme/
│       ├── Color.kt
│       ├── Typography.kt
│       ├── Theme.kt
│       └── Shape.kt
│
└── di/
    ├── NetworkModule.kt
    ├── DatabaseModule.kt
    ├── RepositoryModule.kt
    └── UseCaseModule.kt
```

---

## Modèles de données principaux

```kotlin
// Place.kt (domain model)
data class Place(
    val id: String,
    val name: String,              // multilingue via Map<String, String>
    val description: Map<String, String>, // "fr", "en", "ar"
    val category: Category,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val photos: List<String>,      // URLs
    val rating: Float,
    val reviewCount: Int,
    val openingHours: String?,
    val price: String?,
    val isFavorite: Boolean = false
)

// Category.kt
enum class Category {
    HISTORY, NATURE, FOOD, SHOPPING, EVENTS, BEACH
}

// Itinerary.kt
data class Itinerary(
    val id: String,
    val title: Map<String, String>,
    val description: Map<String, String>,
    val places: List<Place>,
    val durationHours: Int,
    val type: ItineraryType
)

enum class ItineraryType { ONE_DAY, WEEKEND, HISTORICAL, NATURE, CUSTOM }

// User.kt
data class User(
    val uid: String,
    val email: String,
    val displayName: String,
    val photoUrl: String?,
    val preferredLanguage: String = "fr"
)
```

---

## Design System — couleurs et thème

```kotlin
// Color.kt
val TangerGreen = Color(0xFF009966)       // couleur principale (inspiré Visit Rabat)
val TangerGreenLight = Color(0xFFE1F5EE)
val TangerGreenDark = Color(0xFF006644)
val TangerAmber = Color(0xFFEF9F27)       // accent
val TangerCoral = Color(0xFFD85A30)       // warning / attention
val SurfaceLight = Color(0xFFF8F8F8)
val OnSurface = Color(0xFF1A1A1A)
```

**Règles UI :**
- Bottom navigation avec 5 onglets : Home, Map, Itinéraires, Favoris, Profil
- Cards avec : photo + nom + catégorie + distance + note étoiles
- Chips horizontaux pour les filtres de catégorie
- Search bar dans le header de l'écran Home
- Support RTL pour l'arabe (Compose gère automatiquement avec `LocalLayoutDirection`)

---

## Patterns de code à suivre

### ViewModel pattern
```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPlacesUseCase: GetPlacesUseCase,
    private val getPlacesByCategoryUseCase: GetPlacesByCategoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init { loadPlaces() }

    fun loadPlaces() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getPlacesUseCase()
                .catch { e -> _uiState.update { it.copy(error = e.message) } }
                .collect { places -> _uiState.update { it.copy(places = places, isLoading = false) } }
        }
    }

    fun filterByCategory(category: Category?) {
        viewModelScope.launch {
            val flow = if (category == null) getPlacesUseCase()
                       else getPlacesByCategoryUseCase(category)
            flow.collect { places -> _uiState.update { it.copy(places = places, selectedCategory = category) } }
        }
    }
}

data class HomeUiState(
    val places: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCategory: Category? = null,
    val searchQuery: String = ""
)
```

### Screen pattern
```kotlin
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onPlaceClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        uiState.isLoading -> LoadingIndicator()
        uiState.error != null -> ErrorMessage(uiState.error!!)
        else -> HomeContent(uiState = uiState, onPlaceClick = onPlaceClick)
    }
}
```

### Repository pattern
```kotlin
class PlaceRepositoryImpl @Inject constructor(
    private val placeApiService: PlaceApiService,
    private val placeDao: PlaceDao
) : PlaceRepository {

    override fun getPlaces(): Flow<List<Place>> = flow {
        // 1. Émettre d'abord depuis le cache local (offline first)
        val cached = placeDao.getAllPlaces()
        emit(cached.map { it.toDomainModel() })
        // 2. Récupérer depuis l'API et mettre à jour le cache
        try {
            val remote = placeApiService.getPlaces()
            placeDao.insertAll(remote.map { it.toEntity() })
            emit(remote.map { it.toDomainModel() })
        } catch (e: Exception) {
            // Déjà émis le cache, on ignore silencieusement
        }
    }
}
```

---

## Internationalisation (i18n)

```
res/
├── values/strings.xml         ← Français (défaut)
├── values-en/strings.xml      ← Anglais
└── values-ar/strings.xml      ← Arabe
```

```kotlin
// Changer la langue dynamiquement avec DataStore
val userLang by userPreferencesDataStore.language.collectAsState("fr")
CompositionLocalProvider(
    LocalLayoutDirection provides if (userLang == "ar") LayoutDirection.Rtl else LayoutDirection.Ltr
) {
    AppNavGraph()
}
```

---

## Navigation

```kotlin
// Screen.kt
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Map : Screen("map")
    object Detail : Screen("detail/{placeId}") {
        fun createRoute(id: String) = "detail/$id"
    }
    object Itinerary : Screen("itinerary")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
    object Login : Screen("login")
    object Register : Screen("register")
}
```

---

## Fonctionnalités prioritaires (ordre de développement)

1. **Setup** — Structure projet, Hilt, Room, Retrofit, Navigation
2. **Auth** — Login email, SSO Google, SSO Facebook (Firebase)
3. **Design System** — Theme, couleurs, composants réutilisables
4. **Home** — Liste lieux, filtres catégorie, recherche
5. **Map** — Google Maps, pins, bottom sheet
6. **Detail** — Fiche complète, galerie, favoris
7. **Itinéraires** — Templates + affichage
8. **i18n** — Français + Anglais + Arabe RTL
9. **Tests + polish** — Bugs, README, captures

---

## Contraintes importantes

- Tout le code en **Kotlin** — aucun Java
- Toute l'UI en **Jetpack Compose** — aucun XML layout
- Respecter **MVVM strict** : les Screens ne font PAS de logique métier
- Les ViewModels ne doivent PAS avoir de référence au Context Android
- Utiliser `hiltViewModel()` dans tous les Composables qui ont besoin d'un ViewModel
- Toujours utiliser `collectAsStateWithLifecycle()` et non `collectAsState()`
- Les Use Cases ne font **qu'une seule chose** (Single Responsibility)
- Le Repository est la **seule source de vérité** (offline-first avec Room)
- Commits réguliers sur GitHub avec messages conventionnels (`feat:`, `fix:`, `refactor:`)

---

## Données de test (lieux de Tanger)

```kotlin
val samplePlaces = listOf(
    Place(id = "1", name = "Kasbah de Tanger",
          description = mapOf("fr" to "Ancienne citadelle...", "en" to "Ancient citadel..."),
          category = Category.HISTORY, latitude = 35.7907, longitude = -5.8144,
          photos = listOf("https://..."), rating = 4.8f, reviewCount = 342),
    Place(id = "2", name = "Cap Spartel",
          category = Category.NATURE, latitude = 35.7863, longitude = -5.9248,
          rating = 4.9f, reviewCount = 521),
    Place(id = "3", name = "Grottes d'Hercule",
          category = Category.NATURE, latitude = 35.7707, longitude = -5.9215,
          rating = 4.7f, reviewCount = 418),
    Place(id = "4", name = "Médina de Tanger",
          category = Category.HISTORY, latitude = 35.7896, longitude = -5.8137,
          rating = 4.6f, reviewCount = 287),
    Place(id = "5", name = "Grand Socco",
          category = Category.SHOPPING, latitude = 35.7878, longitude = -5.8101,
          rating = 4.5f, reviewCount = 195),
    Place(id = "6", name = "Plage Malabata",
          category = Category.BEACH, latitude = 35.7997, longitude = -5.7891,
          rating = 4.4f, reviewCount = 156)
)
```

---

## Comment utiliser ce fichier avec un agent IA

### Avec Claude / ChatGPT
```
Voici le contexte de mon projet Android : [coller le contenu de ce fichier]

Ma question : Comment implémenter [feature] ?
```

### Avec GitHub Copilot
- Ouvrir ce fichier dans l'éditeur avant de coder
- Copilot l'utilisera automatiquement comme contexte

### Avec Cursor
- Ajouter ce fichier dans `@codebase` ou dans les règles du projet `.cursorrules`

### Questions types à poser à l'agent

- "Génère le code complet de `HomeScreen.kt` en respectant le contexte du projet"
- "Écris `PlaceRepositoryImpl.kt` avec le pattern offline-first décrit"
- "Crée le `NetworkModule.kt` Hilt avec Retrofit pour ce projet"
- "Comment intégrer Google Sign-In avec Firebase dans `AuthViewModel.kt` ?"
- "Génère les strings.xml pour le français, anglais et arabe"
- "Crée un composant Compose `PlaceCard` réutilisable avec le Design System"

---

*Dernière mise à jour : Mai 2026 — Groupe 10, Master DevOps & Cloud Computing*
