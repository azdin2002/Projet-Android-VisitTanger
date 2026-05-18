# AI_AGENT.md — Visit Tanger

> **Source of truth:** This document is generated from the **current codebase** (`app/src/main`, Gradle files). Do not trust outdated README claims without verifying source. Last regenerated: 2026-05-18.

---

## 1. Project Identity

| Field | Value |
|-------|-------|
| **App name** | Visit Tanger |
| **Application ID / package** | `com.groupe10.visittanger` |
| **Project type** | Native Android app (single-module) |
| **Architecture level** | Layered MVVM + Use Cases (Clean Architecture–inspired, **not** multi-module) |
| **Purpose** | Tourism app for discovering Tangier: places, map, itineraries, favorites, weather, profile |
| **Target users** | Tourists and residents exploring Tangier |
| **Academic context** | Groupe 10 — UAE Larache, Master DevOps & Cloud Computing 2025/2026 |

### Repository structure

```
VisitTanger/
├── app/                          # Single Android application module
│   ├── build.gradle.kts
│   ├── google-services.json
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/groupe10/visittanger/
│       │   ├── data/             # Repositories, Room, Firestore, Retrofit, DataStore
│       │   ├── domain/           # Models, repository interfaces, use cases
│       │   ├── di/               # Hilt modules
│       │   └── ui/               # Compose screens, ViewModels, theme, navigation
│       └── res/                  # strings (fr/en/ar), themes, mipmaps — **no layout XML**
├── gradle/libs.versions.toml
├── settings.gradle.kts           # include(":app") only
└── build.gradle.kts
```

**Modularization:** None. All layers live inside `:app` under package prefixes (`data`, `domain`, `ui`).

---

## 2. REAL Tech Stack

Only technologies **actually present** in `app/build.gradle.kts` and source:

| Category | Technology | Notes |
|----------|------------|-------|
| Language | **Kotlin** 2.2.10 | Built-in with AGP 9.2 |
| UI | **Jetpack Compose** + **Material 3** | `compose = true`; no `res/layout` screens |
| Navigation | **Navigation Compose** 2.7.7 | Single `NavHost` in `AppNavGraph` |
| DI | **Hilt** 2.59.2 + **KSP** | `@HiltAndroidApp`, `@HiltViewModel` |
| Async | **Coroutines** + **Flow** / **StateFlow** | No RxJava |
| Lifecycle | **lifecycle-runtime-ktx** 2.10.0 | `collectAsStateWithLifecycle` in screens |
| Local DB | **Room** 2.8.4 (v5) | `favorites`, `visited_places`, `places` |
| Preferences | **DataStore Preferences** 1.0.0 | Dark mode + language |
| HTTP | **Retrofit** 2.9.0 + **Gson** + **OkHttp** logging | **Weather API only** |
| Images | **Coil** 2.6.0 (`coil-compose`) | `AsyncImage` in cards/galleries |
| Maps | **Maps Compose** 8.3.0 + Play Services Maps/Location | |
| Backend | **Firebase** BOM 34.13.0 | Auth, Analytics, **Firestore** |
| SSO | **Google Sign-In** + **Facebook Login** SDK | |
| Adaptive UI | **material3-window-size-class** + **Window** library | Phone/tablet layouts |
| Build | AGP 9.2.1, `compileSdk`/`targetSdk` **36**, `minSdk` **26** | |

### NOT used (do not add without explicit request)

- Jetpack **Paging**
- **WebSocket**
- **SharedPreferences** (DataStore is used instead)
- Retrofit **Place/Auth APIs** (no `PlaceApiService` / `AuthApiService` in codebase)
- **Mockk** in Gradle (README mentions it; not in dependencies)
- XML View-based UI (`res/layout` is empty)
- Multi-module Gradle structure
- Deeplinks (`navDeepLink` not found)
- Custom token manager / OkHttp auth interceptors

---

## 3. Current Project Architecture

### Pattern: MVVM + Repository + Use Cases

```
┌─────────────────────────────────────────────────────────────┐
│  UI (Jetpack Compose)                                        │
│  Screen → hiltViewModel() → collectAsStateWithLifecycle()   │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│  ViewModel (@HiltViewModel)                                  │
│  MutableStateFlow<UiState> → StateFlow                       │
│  Optional: MutableSharedFlow for one-shot events             │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│  Domain: Use Cases (@Inject) → Repository interfaces         │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│  Data: *RepositoryImpl                                       │
│  • Firestore (places) + Room cache                           │
│  • Firebase Auth                                             │
│  • Room (favorites, visited, places cache)                   │
│  • Retrofit (OpenWeatherMap)                                 │
│  • Mock data (itineraries, reviews)                          │
└─────────────────────────────────────────────────────────────┘
```

### Data flow characteristics

- **Places:** Firestore `places` collection → DTO (`PlaceDto`) → domain `Place` → Room `places` table as cache → UI via `Flow`.
- **Offline-first (partial):** `PlaceRepositoryImpl` emits Room cache first, then refreshes from Firestore.
- **Favorites:** Room only, scoped by Firebase `userId` (composite PK `placeId` + `userId`).
- **Auth:** Firebase Auth only; no REST login API.
- **Itineraries / reviews:** In-memory mock (`ItineraryMockData`, `ReviewMockData`), not persisted remotely.

### Adaptive navigation shell

`MainScreen` chooses chrome by window width:

| Width class | Navigation UI |
|-------------|-----------------|
| Compact | `Scaffold` + `TangerBottomNavBar` |
| Medium | `NavigationRail` |
| Expanded | `PermanentNavigationDrawer` + `DrawerNavContent` |

Tab navigation uses custom `navigateToTab()` with `popUpTo(Screen.Home.route)` — **not** `findStartDestination()` (avoids login-route bugs after auth).

---

## 4. Full Package Structure

Verified Kotlin files under `com.groupe10.visittanger`:

```
com.groupe10.visittanger/
├── MainActivity.kt
├── VisitTangerApp.kt
│
├── data/
│   ├── auth/
│   │   └── GoogleSignInIntentProvider.kt
│   ├── datastore/
│   │   └── UserPreferencesDataStore.kt
│   ├── local/
│   │   ├── AppDatabase.kt
│   │   ├── converter/MapConverter.kt
│   │   ├── dao/
│   │   │   ├── FavoriteDao.kt
│   │   │   ├── PlaceDao.kt
│   │   │   └── VisitedPlaceDao.kt
│   │   └── entity/
│   │       ├── FavoriteEntity.kt
│   │       ├── PlaceEntity.kt
│   │       └── VisitedPlaceEntity.kt
│   ├── mock/
│   │   ├── ItineraryMockData.kt
│   │   ├── PlaceMockData.kt
│   │   └── ReviewMockData.kt
│   ├── remote/
│   │   ├── FirestoreSeeder.kt
│   │   ├── api/WeatherApiService.kt
│   │   └── dto/
│   │       ├── PlaceDto.kt
│   │       └── WeatherDto.kt
│   └── repository/
│       ├── AuthRepositoryImpl.kt
│       ├── FavoriteRepositoryImpl.kt
│       ├── ItineraryRepositoryImpl.kt
│       ├── PlaceRepositoryImpl.kt
│       ├── UserRepositoryImpl.kt
│       ├── VisitedPlaceRepositoryImpl.kt
│       └── WeatherRepositoryImpl.kt
│
├── domain/
│   ├── model/
│   │   ├── Category.kt
│   │   ├── Itinerary.kt
│   │   ├── ItineraryStop.kt
│   │   ├── ItineraryType.kt
│   │   ├── Place.kt
│   │   ├── Review.kt
│   │   ├── User.kt
│   │   └── Weather.kt
│   ├── repository/          # 7 interfaces
│   └── usecase/           # 16 use cases (see §11)
│
├── di/
│   ├── AuthModule.kt
│   ├── DatabaseModule.kt
│   ├── FirestoreModule.kt
│   ├── ItineraryModule.kt
│   ├── NetworkModule.kt
│   ├── PlaceModule.kt
│   ├── UserModule.kt
│   └── WeatherModule.kt
│
└── ui/
    ├── adaptive/
    │   ├── PreviewDevices.kt
    │   └── WindowSizeUtils.kt      # DeviceType, AdaptiveLayoutConfig
    ├── auth/
    │   ├── AuthViewModel.kt
    │   ├── LoginScreen.kt
    │   └── RegisterScreen.kt
    ├── components/                 # Shared composables (§13)
    ├── detail/
    ├── favorites/
    ├── home/
    ├── itinerary/
    │   └── detail/
    ├── language/
    ├── main/                       # Bottom nav, drawer
    ├── map/
    ├── navigation/
    ├── profile/
    └── theme/
```

---

## 5. UI Architecture

### Design system (`ui/theme/`)

| File | Role |
|------|------|
| `Color.kt` | Brand: `TangerGreen`, `TangerAmber`, `TangerCoral`, `SurfaceLight`, etc. |
| `Typography.kt` | Material3 `Typography` (minimal override: `bodyLarge` only) |
| `Theme.kt` | `VisitTangerTheme(darkTheme)` — light/dark schemes; `dynamicColor = false` by default |
| `CategoryExtensions.kt` | `Category.toLocalizedName()` for UI labels |

**Dark mode:** Driven by `UserPreferencesDataStore.isDarkMode`, collected in `MainActivity`, passed to `VisitTangerTheme`.

**Category colors:** `getCategoryColors(category)` in `CategoryChip.kt` — also used by `PlaceCard` (same file).

### Scaffold / screen patterns

- **Tab screens:** Content only; shell from `MainScreen` (bottom bar / rail / drawer).
- **Detail / auth / itinerary detail:** Full-screen; nav chrome hidden via `hideNavRoutes` in `MainScreen`.
- **Top bar:** `TangerTopBar` (green `CenterAlignedTopAppBar`) on detail/itinerary detail.
- **Loading:** `LoadingIndicator` (full-screen centered `CircularProgressIndicator`).
- **Error:** `ErrorView(message, onRetry)` or inline error text in `UiState`.
- **Empty:** `EmptyView` (home/search) or feature-specific `EmptyFavoritesView`.

### Navigation patterns

- Routes defined in `Screen` sealed class.
- Args: `placeId`, `itineraryId` via `navArgument`.
- Callbacks: `onPlaceClick`, `onBackClick`, `onLogoutSuccess` passed from `AppNavGraph`.
- Logout: `ProfileScreen` → `AuthViewModel.resetAuthUiState()` + `navController.navigate(Login) { popUpTo(0) { inclusive = true } }`.

### Internationalization

- **App locale:** `LanguageManager` + `UserPreferencesDataStore.language` + `attachBaseContext` in `MainActivity` (only allowed `runBlocking` in project).
- **RTL:** `CompositionLocalProvider(LocalLayoutDirection)` when `ar`.
- **Place content:** `Place.description: Map<String, String>` keys `fr` / `en` / `ar`.
- **Static strings:** `res/values/strings.xml`, `values-en`, `values-ar`.

---

## 6. State Management

### Standard ViewModel pattern

```kotlin
private val _uiState = MutableStateFlow(XxxUiState())
val uiState: StateFlow<XxxUiState> = _uiState.asStateFlow()
```

Screens collect with:

```kotlin
val uiState by viewModel.uiState.collectAsStateWithLifecycle()
```

### UiState inventory

| Screen | State type | Notable fields |
|--------|------------|----------------|
| Auth | `AuthUiState` (data class) | `isLoading`, `isSuccess`, `error`, `user` |
| Home | `HomeUiState` | `places`, `filteredPlaces`, `featuredPlaces`, `selectedCategory`, `searchQuery` |
| Home weather | `WeatherUiState` (**sealed**) | `Loading`, `Success`, `Error`, `Hidden` |
| Map | `MapUiState` | places, selection, filters |
| Detail | `DetailUiState` | `place`, `reviews`, `selectedPhotoIndex` |
| Favorites | `FavoritesUiState` | favorites list, loading |
| Itinerary | `ItineraryUiState` | itineraries, filter type |
| Itinerary detail | `ItineraryDetailUiState` | itinerary, stops |
| Profile | `ProfileUiState` | user, stats, dialogs, dark mode, language |

### Events (one-shot)

- `ProfileViewModel.navigationEvent: SharedFlow<String>` — logout → `"login"` (screen also uses `onLogoutSuccess` callback from graph).
- `UserRepositoryImpl.profileRefresh: MutableSharedFlow` — force UI refresh after Firebase display name update.

### Side effects

- `viewModelScope.launch` for repository/use case calls.
- Auth/social sign-in uses `Dispatchers.IO` in `AuthViewModel`.
- Search debounce: 300ms `delay` in `HomeViewModel.onSearchQueryChanged`.

### Lifecycle

- Prefer `collectAsStateWithLifecycle()` (not deprecated `collectAsState()`).
- `MainActivity` passes `lifecycle` explicitly for dark mode Flow collection.

---

## 7. Networking Layer

### Retrofit (weather only)

| Item | Value |
|------|-------|
| Module | `NetworkModule` + `WeatherModule` |
| Base URL | `https://api.openweathermap.org/` (`@Named("weather")`) |
| API | `WeatherApiService.getCurrentWeather(lat, lon, appid, …)` |
| API key | `BuildConfig.WEATHER_API_KEY` from `local.properties` → `OPENWEATHER_API_KEY` |
| Interceptor | `HttpLoggingInterceptor` **BODY** level only |
| Auth interceptor | **None** |

### Firebase Firestore (places)

| Item | Value |
|------|-------|
| Instance | Named Firestore DB `"places"` via `FirestoreModule` |
| Collection | `places` |
| DTO | `PlaceDto` → `toDomain()` |
| Seeder | `FirestoreSeeder.seedPlacesIfEmpty()` on app start (`VisitTangerApp` + `MainActivity`) |

### Firebase Authentication

| Method | Implementation |
|--------|----------------|
| Email/password | `AuthRepositoryImpl.loginWithEmail` / `registerWithEmail` |
| Google | `GoogleSignInIntentProvider` → id token → `GoogleAuthProvider` |
| Facebook | `LoginManager` + `FacebookAuthProvider` |
| Session | `FirebaseAuth.currentUser`; `isUserLoggedIn()` for start destination |

**No** JWT token manager, refresh interceptor, or Retrofit auth header layer.

### Error handling

- Repository methods return `Result<T>` for auth/profile ops.
- UI states expose `error: String?` + `clearError()` on ViewModels.
- Firestore fetch failures in `PlaceRepositoryImpl` fall back to cache via `runCatching`.

---

## 8. Local Storage

### Room (`visit_tanger_db`, version 5)

| Table | Entity | DAO | Purpose |
|-------|--------|-----|---------|
| `favorites` | `FavoriteEntity` | `FavoriteDao` | Per-user saved places (denormalized place fields) |
| `visited_places` | `VisitedPlaceEntity` | `VisitedPlaceDao` | Track visited places per user |
| `places` | `PlaceEntity` | `PlaceDao` | Firestore cache |

**Type converter:** `MapConverter` for `Map<String, String>` / list fields as needed.

**Migrations:** `MIGRATION_1_2` … `MIGRATION_4_5` in `AppDatabase` (user-scoped favorites, visited places, places cache).

### DataStore (`user_preferences`)

| Key | Type | Default |
|-----|------|---------|
| `dark_mode` | Boolean | `false` |
| `language` | String | `"fr"` |

### Caching strategy

1. **Places:** Room first if non-empty → Firestore fetch → update Room → merge favorite flags from `FavoriteDao`.
2. **Favorites:** Room Flow per `userId`; reactive to `FirebaseAuth` state via `callbackFlow`.
3. **Itineraries/reviews:** Not cached — mock only.

---

## 9. Dependency Injection

### Application entry

- `@HiltAndroidApp` on `VisitTangerApp`
- `@AndroidEntryPoint` on `MainActivity`
- `@HiltViewModel` on all ViewModels

### Hilt modules (`di/`)

| Module | Provides / Binds |
|--------|------------------|
| `AuthModule` | `AuthRepository` ← `AuthRepositoryImpl`; `FirebaseAuth` |
| `PlaceModule` | `PlaceRepository` ← `PlaceRepositoryImpl` |
| `DatabaseModule` | `AppDatabase`, DAOs, `FavoriteRepository`, `VisitedPlaceRepository` |
| `FirestoreModule` | `FirebaseFirestore` (named DB), `FirestoreSeeder` |
| `NetworkModule` | `OkHttpClient`, `@Named("weather") Retrofit` |
| `WeatherModule` | `WeatherApiService`, `WeatherRepository` |
| `ItineraryModule` | `ItineraryRepository` |
| `UserModule` | `UserRepository` |

**Use cases:** Constructor `@Inject` only — no dedicated `UseCaseModule` (Hilt resolves automatically).

**Entry point:** `UserPreferencesEntryPoint` in `MainActivity` for DataStore access in `attachBaseContext` (before field injection).

### Singletons

Repositories marked `@Singleton` where needed (`PlaceRepositoryImpl`, `AuthRepositoryImpl`, etc.).

---

## 10. Navigation System

### Routes (`Screen.kt`)

| Route | Pattern | In bottom nav |
|-------|---------|---------------|
| `home` | — | Yes |
| `map` | — | Yes |
| `itinerary` | — | Yes |
| `favorites` | — | Yes |
| `profile` | — | Yes |
| `login` | — | No (start if logged out) |
| `register` | — | No |
| `details/{placeId}` | Argument | No (hides nav) |
| `itinerary_detail/{itineraryId}` | Argument | No (hides nav) |

### Graph

- **File:** `AppNavGraph.kt` — single `NavHost`.
- **Start destination:** `Home` if `authViewModel.isUserLoggedIn()`, else `Login`.
- **Nested navigation:** No separate nested graphs; one flat host inside adaptive shell.
- **Deeplinks:** **Not implemented.**

### Auth navigation

- Login/Register success → navigate to `Home`.
- Logout → clear stack to `Login`.

---

## 11. Features List (implemented)

| Feature | Status | Data source |
|---------|--------|-------------|
| Email register/login | ✅ | Firebase Auth |
| Google SSO | ✅ | Firebase + Play Services Auth |
| Facebook SSO | ✅ | Facebook SDK + Firebase |
| Home — place list | ✅ | Firestore + Room cache |
| Home — category filter | ✅ | Client-side on Flow |
| Home — search | ✅ | Debounced client filter |
| Home — featured strip | ✅ | `places.take(3)` |
| Weather widget | ✅ | OpenWeatherMap Retrofit |
| Interactive map | ✅ | Maps Compose + place markers |
| Map bottom sheet | ✅ | `PlaceBottomSheet` |
| Place detail | ✅ | Firestore/Room + mock reviews |
| Mark visited | ✅ | `VisitedPlaceRepository` / Room |
| Favorites | ✅ | Room per user |
| Swipe-to-delete favorite | ✅ | `SwipeToDeleteFavoriteCard` |
| Itinerary templates | ✅ | **Mock** (`ItineraryMockData`) |
| Itinerary detail | ✅ | Mock |
| Profile — stats | ✅ | Favorites count, visited count |
| Profile — edit display name | ✅ | Firebase `updateProfile` |
| Dark mode toggle | ✅ | DataStore |
| Language selector (fr/en/ar) | ✅ | DataStore + `LanguageManager` |
| Adaptive tablet/landscape UI | ✅ | Window size classes |
| Firestore seed on first run | ✅ | `FirestoreSeeder` |

### Not implemented / README aspirational only

- Custom itinerary creation / export / share
- Real user reviews API
- Push notifications
- Onboarding flow
- Paging for long lists
- Dedicated events calendar backend

---

## 12. Coding Standards (inferred)

### Naming

- **Screens:** `*Screen.kt` composable entry points.
- **ViewModels:** `*ViewModel.kt` with matching `*UiState.kt` where non-trivial.
- **Repositories:** Interface in `domain/repository`, impl in `data/repository/*Impl.kt`.
- **Use cases:** `VerbNounUseCase.kt`, `operator fun invoke(...)`.
- **Routes:** `Screen` sealed object, `createRoute(id)` helpers for parameterized routes.

### ViewModel conventions

- Private `MutableStateFlow`, public `StateFlow`.
- `viewModelScope.launch` for async work.
- `_uiState.update { it.copy(...) }` for immutable state updates.
- Optional `clearError()` mirroring across ViewModels.

### Composable conventions

- Stateless child composables receive `uiState` + callbacks (`HomeScreen` pattern).
- `hiltViewModel()` at screen root.
- `Modifier` last parameter where applicable.
- `@Preview` on shared components.

### Package placement

- Feature UI under `ui/<feature>/`.
- Truly shared widgets under `ui/components/`.
- Navigation under `ui/navigation/`.
- Do **not** put business logic in composables beyond UI mapping.

### Resources

- Secrets in `local.properties`: `MAPS_API_KEY`, `OPENWEATHER_API_KEY`, `FACEBOOK_APP_ID`, `FACEBOOK_CLIENT_TOKEN`.
- Never commit real keys; `google-services.json` is project-specific.

---

## 13. Reusable Components Inventory

### `ui/components/` (use before creating new widgets)

| Component | File | Purpose |
|-----------|------|---------|
| `PlaceCard` | `PlaceCard.kt` | Place list card: image, name, category badge, rating, favorite |
| `CategoryChip` | `CategoryChip.kt` | Filter chip per `Category`; includes `getCategoryColors()` |
| `TangerSearchBar` | `SearchBar.kt` | Rounded search field with clear action |
| `TangerTopBar` | `TangerTopBar.kt` | Green centered top app bar with optional back |
| `RatingBar` | `RatingBar.kt` | Star rating + review count |
| `LoadingIndicator` | `LoadingIndicator.kt` | Full-screen loading spinner |
| `ErrorView` | `ErrorView.kt` | Error icon + message + retry button |
| `EmptyView` | `EmptyView.kt` | Generic empty state with icon |

### `ui/main/`

| Component | Purpose |
|-----------|---------|
| `TangerBottomNavBar` | Bottom navigation for compact width |
| `DrawerNavContent` | Permanent drawer items (tablet) |
| `NavItem` | Data class: screen, icons, label |

### `ui/home/` (weather subsystem)

| Component | Purpose |
|-----------|---------|
| `WeatherWidget` | Animated visibility wrapper |
| `WeatherWidgetContent` | Success UI |
| `WeatherWidgetSkeleton` | Loading placeholder |
| `WeatherWidgetError` | Error + dismiss |

### `ui/favorites/`

| Component | Purpose |
|-----------|---------|
| `EmptyFavoritesView` | CTA to explore home |
| `SwipeToDeleteFavoriteCard` | Dismissible favorite row |

### `ui/profile/`

| Component | Purpose |
|-----------|---------|
| `ProfileAvatarSection` | Avatar + name edit entry |
| `ProfileStatsSection` | Favorites / visited / itinerary counts |
| `ProfileMenuItem` | Settings row |
| `ProfileToggleItem` | Switch row (e.g. dark mode) |
| `ProfileSectionTitle` | Section header |
| `EditNameDialog` | Rename dialog |
| `LogoutConfirmDialog` | Logout confirmation |

### `ui/language/`

| Component | Purpose |
|-----------|---------|
| `LanguageSelectorDialog` | Pick fr / en / ar |

### `ui/itinerary/`

| Component | Purpose |
|-----------|---------|
| `ItineraryCard` | Itinerary list item |
| `FeaturedItineraryCard` | Highlighted itinerary |
| `ItineraryStopItem` | Stop row in detail |

### `ui/map/`

| Component | Purpose |
|-----------|---------|
| `PlaceBottomSheet` | Quick place preview on map |
| `CategoryMarker` | Map marker styling |
| `LocationPermissionHandler` | Permission UX composable |

### Reuse rules

1. Import from `ui.components` for cards, chips, search, loading, errors.
2. Use `TangerGreen` / theme colors — avoid new hardcoded `0xFF009966` when `Color.kt` exists.
3. Use `getCategoryColors()` for category badges — do not duplicate color maps.
4. Feature-specific empty states stay in feature package unless used ≥2 times.

---

## 14. AI Development Rules

### Must do

1. **Compose only** for screens — never add `res/layout` XML UIs.
2. **Follow existing layers:** UI → ViewModel → UseCase → Repository → (Firestore/Room/Retrofit).
3. **Inject with Hilt** — no manual singletons except documented patterns (`LanguageManager` object is OK).
4. **Reuse** components from §13 before creating duplicates.
5. **Use `collectAsStateWithLifecycle()`** in composables collecting Flow/StateFlow.
6. **Scope favorites/visited by Firebase `userId`** — match `FavoriteDao` / `VisitedPlaceDao` queries.
7. **Places data** goes through `PlaceRepository` / Firestore — do not invent REST place endpoints.
8. **Keep navigation routes** in `Screen.kt`; wire new screens in `AppNavGraph.kt` and update `hideNavRoutes` if full-screen.
9. **Strings:** add to `values/strings.xml` + `values-en` + `values-ar` for static UI text.
10. **Secrets** only via `local.properties` / `BuildConfig` — never hardcode API keys.
11. **Respect adaptive shell** — test compact/medium/expanded behavior in `MainScreen`.
12. **Only `runBlocking` in `MainActivity.attachBaseContext`** for locale — nowhere else.

### Must not do

1. Do not add Retrofit `PlaceApiService` / `AuthApiService` unless product explicitly migrates off Firebase.
2. Do not use SharedPreferences for new prefs — use `UserPreferencesDataStore`.
3. Do not bypass Use Cases for cross-layer calls from ViewModels (existing code is mostly consistent).
4. Do not create a second favorite-toggle code path — consolidate on `FavoriteRepository` (see §15).
5. Do not enable dynamic Material You colors without product approval (`dynamicColor = false` by default).
6. Do not add multi-module splits without team agreement — project is single-module.
7. Do not commit modified `google-services.json` with wrong package/SHA without team notice.

### When adding a feature

1. Domain model → repository interface → impl → use case(s) → ViewModel + UiState → Screen composable.
2. Register bindings in appropriate `di/*Module.kt` if new repository.
3. Add Room migration if schema changes — never bump `@Database version` without `Migration`.
4. Update this `AI_AGENT.md` if architecture changes materially.

---

## 15. Recommended Refactors / Improvements

| Priority | Item |
|----------|------|
| High | **Unify favorite toggling:** `PlaceRepository.toggleFavorite` vs `FavoriteRepository.toggleFavorite` + two use cases (`ToggleFavoriteUseCase` vs `ToggleFavoriteWithRepoUseCase`). Map uses one path; Home/Detail/Favorites use the other. |
| High | **Replace hardcoded `Color(0xFF009966)`** in components with `TangerGreen` / `MaterialTheme.colorScheme` (20+ files). |
| Medium | **Persist itineraries** (Firestore or Room) instead of `ItineraryMockData`. |
| Medium | **Real reviews backend** or Firestore subcollection — remove `ReviewMockData` from production path. |
| Medium | **Align README** with Firebase/Firestore reality (remove PlaceApiService/SharedPreferences/Mockk claims). |
| Medium | **Remove duplicate `FirestoreSeeder` calls** (`VisitTangerApp` + `MainActivity` both seed). |
| Low | Expand `Typography.kt` beyond `bodyLarge`. |
| Low | Add deeplinks for `details/{placeId}` if marketing needs them. |
| Low | Add Paging if place list grows large. |
| Low | Add unit tests + Mockk (README claims tests; only example tests exist). |

---

## Appendix A — OLD vs CURRENT (vs README / prior docs)

No prior `AI_AGENT.md` was in the repo; comparison is against **README.md** and typical earlier MVVM templates.

| Topic | OLD (README / typical) | CURRENT (codebase) |
|-------|------------------------|---------------------|
| Place API | Retrofit `PlaceApiService` | **Firebase Firestore** + `PlaceDto` |
| Auth API | Retrofit `AuthApiService` | **Firebase Auth** only |
| Preferences | DataStore + SharedPreferences | **DataStore only** |
| Room tables | `PlaceDao`, `FavoriteDao` | + `VisitedPlaceDao`, **`places` cache table**, user-scoped favorites |
| Itineraries | Described as full feature | **Mock data only** |
| Reviews | User reviews feature | **Mock** (`ReviewMockData`) |
| Navigation | Basic bottom nav | **Adaptive** bottom bar / rail / drawer |
| Weather | Mentioned | **Implemented** via OpenWeatherMap |
| Visited places | Not documented | **Implemented** (Room + profile stats) |
| SSO | Google + Facebook | **Implemented** in `AuthViewModel` |
| Modularization | Implied layers | **Single module**, package-layered |
| Tests | JUnit + Mockk | **Example tests only**, no Mockk dep |

---

## Appendix B — Technical Debt

1. Dual favorite repositories and use cases (inconsistent Map vs other screens).
2. Mock itineraries/reviews presented as production UI.
3. Widespread hardcoded brand colors instead of theme tokens.
4. `FirestoreSeeder` invoked twice at startup.
5. `getPlaceById` loads entire places Flow then `.find` — inefficient for detail-only fetch.
6. Minimal typography / design token coverage.
7. README inaccuracy risks misleading new contributors and AI agents.
8. `ProfileViewModel.navigationEvent` vs callback logout — two patterns for same concern.
9. No ProGuard/R8 minify in release (`isMinifyEnabled = false`).
10. Example unit/instrumentation tests only — no domain/repository test coverage.

---

## Appendix C — Reusable UI Components (summary list)

`PlaceCard`, `CategoryChip`, `getCategoryColors`, `TangerSearchBar`, `TangerTopBar`, `RatingBar`, `LoadingIndicator`, `ErrorView`, `EmptyView`, `TangerBottomNavBar`, `DrawerNavContent`, `WeatherWidget` (+ Content/Skeleton/Error), `EmptyFavoritesView`, `SwipeToDeleteFavoriteCard`, `ProfileAvatarSection`, `ProfileStatsSection`, `ProfileMenuItem`, `ProfileToggleItem`, `ProfileSectionTitle`, `EditNameDialog`, `LogoutConfirmDialog`, `LanguageSelectorDialog`, `ItineraryCard`, `FeaturedItineraryCard`, `ItineraryStopItem`, `PlaceBottomSheet`, `CategoryMarker`, `LocationPermissionHandler`.

---

## Appendix D — Duplicated Code Candidates

| Area | Files / symbols | Notes |
|------|-----------------|-------|
| Favorite toggle | `PlaceRepositoryImpl.toggleFavorite`, `FavoriteRepositoryImpl.toggleFavorite`, `ToggleFavoriteUseCase`, `ToggleFavoriteWithRepoUseCase` | Same behavior, two stacks |
| Firebase UID Flow | `PlaceRepositoryImpl.firebaseUidFlow()`, `FavoriteRepositoryImpl.firebaseUidFlow()` | Identical `callbackFlow` pattern |
| Brand color | `Color(0xFF009966)` vs `TangerGreen` | Repeated across 25+ references |
| Firestore seeding | `VisitTangerApp.onCreate`, `MainActivity.onCreate` | Duplicate `seedPlacesIfEmpty()` |
| Auth logout | `AuthViewModel.logout`, `LogoutUseCase`, `UserRepositoryImpl.logout`, `AuthRepositoryImpl.logout` | Overlapping responsibilities |
| Category label | `Category.labelFr` in `PlaceCard` vs `toLocalizedName()` in chips | Inconsistent i18n for category text |

---

## Appendix E — Architecture Inconsistencies

1. **Clean Architecture boundary:** Use cases exist, but some ViewModels call `AuthRepository` directly (`AuthViewModel`) while others use use cases exclusively.
2. **Data sources:** Places are remote-first Firestore; itineraries/reviews are purely local mock — inconsistent domain maturity.
3. **Repository binding style:** Mix of `@Binds` (`PlaceModule`, `AuthModule`) and `@Provides` delegating to impl (`UserModule`, `ItineraryModule`, `WeatherModule`).
4. **Favorite state on Map:** Uses `ToggleFavoriteUseCase` → `PlaceRepository`; other screens use `FavoriteRepository` — favorite UI may desync until places Flow refreshes.
5. **Navigation events:** Profile uses `SharedFlow` + parent callback; other screens use lambda parameters only.
6. **i18n:** App-level locale via DataStore + per-place `description` map + some hardcoded French composable strings.
7. **README vs code:** Documented Retrofit place/auth APIs and SharedPreferences do not exist — treat README as partially stale.

---

*End of AI_AGENT.md*
