# Visit Tanger

Visit Tanger is an Android app built to help tourists and locals explore the city of Tangier. You can browse places of interest, see them on a map, save favorites, follow suggested itineraries, and switch between French, English, and Arabic. The project was developed as part of our Master's program in DevOps and Cloud Computing at Université Abdelmalek Essaâdi, Faculté Polydisciplinaire de Larache (2025/2026), under the supervision of Pr. KOUISSI Mohamed.

## About the project

Tangier has a lot to offer — historic sites like the Kasbah and the Medina, natural spots such as Cap Spartel and the Hercules Caves, beaches, local food, markets, and cultural events. Visit Tanger brings these together in one place so you can discover the city at your own pace, whether you are planning a day trip or a longer stay.

The app covers the main things you would expect from a city guide: account creation and login, a home screen with search and category filters, an interactive map, detailed place pages, curated itineraries, offline favorites, a user profile, and live weather for Tangier.

## Team

This is **Groupe 10**. Wail CHAIRI MAHJOR worked on the UI layer — Jetpack Compose screens, navigation, and the design system. Azzeddine SALMOUN handled the data side — repositories, ViewModels, Room, Firebase, and API integration.

## What the app does

**Authentication** — Users can register and log in with email and password. Google and Facebook sign-in are also supported through Firebase Authentication.

**Home** — The main screen highlights popular places, lets you filter by category (History, Nature, Food, Shopping, Events, Beach), search by name, and shows the current weather in Tangier via the OpenWeather API.

**Map** — Places appear on a Google Map with markers colored by category. You can tap a marker to open a quick preview, then go to the full detail page or get directions.

**Place details** — Each place has photos, a description (available in multiple languages), opening hours, pricing when relevant, ratings, and the option to add it to favorites or open navigation in Google Maps.

**Itineraries** — Pre-built routes are available (one day in Tangier, weekend, historical tour, nature escape, and more). You can browse the list of stops for each itinerary and jump to any place from there.

**Favorites** — Saved places are stored locally with Room, so they remain available even without a network connection.

**Profile and settings** — Manage your account, change the app language (French, English, or Arabic with RTL support), and log out.

**Adaptive layout** — The interface adjusts for phones and tablets, in both portrait and landscape.

## Tech stack

The app is written entirely in **Kotlin**. The UI uses **Jetpack Compose** with **Material 3**. We follow **MVVM** architecture with a clean separation between UI, domain, and data layers. **Hilt** handles dependency injection. Local data goes through **Room** and **DataStore**. Remote data comes from **Firebase Firestore** (places and itineraries) and **Retrofit** (weather). Images are loaded with **Coil**. Maps use the **Google Maps Compose** library. Authentication runs on **Firebase Auth**, with Google Sign-In and the Facebook SDK for social login.

| Requirement | Version |
|-------------|---------|
| Min SDK | 26 |
| Target SDK | 36 |
| Compile SDK | 36 |
| Kotlin | 2.2.10 |
| AGP | 9.2.1 |

## Architecture

The codebase is organized in three layers. The **UI layer** contains Compose screens and ViewModels that expose state through `StateFlow`. The **domain layer** holds business models, repository interfaces, and use cases — each use case does one thing. The **data layer** implements those repositories, combining Firestore, Retrofit, Room, and DataStore. Hilt wires everything together.

```
app/src/main/java/com/groupe10/visittanger/
├── data/          remote APIs, Firestore, Room, DataStore, repository implementations
├── domain/        models, repository interfaces, use cases
├── ui/            screens, ViewModels, navigation, theme
└── di/            Hilt modules
```

Data for places follows an offline-first approach: Room caches what Firestore provides, so the app can still show content when the network is slow or unavailable.

## Getting started

### Prerequisites

You need **Android Studio** (recent version, Ladybug or newer recommended) with the Android SDK installed. The project targets API 36 and requires a device or emulator running at least API 26. A JDK 11+ is used for compilation.

### Clone and open

```bash
git clone https://github.com/azdin2002/Projet-Android-VisitTanger.git
cd Projet-Android-VisitTanger
```

Open the project folder in Android Studio and let Gradle sync finish.

### Configuration

Before the app can build, you need a `local.properties` file at the project root (this file is gitignored and must be created locally). At minimum, add your Android SDK path and a Google Maps API key:

```properties
sdk.dir=C\:\\Users\\YourName\\AppData\\Local\\Android\\Sdk
MAPS_API_KEY=your_google_maps_api_key
```

Optional keys — the app will still compile without them, but some features will be limited:

```properties
OPENWEATHER_API_KEY=your_openweather_api_key
FACEBOOK_APP_ID=your_facebook_app_id
FACEBOOK_CLIENT_TOKEN=your_facebook_client_token
```

**Google Maps** — Create a key in the [Google Cloud Console](https://console.cloud.google.com/), enable the Maps SDK for Android, and restrict it to your app's package name (`com.groupe10.visittanger`).

**Firebase** — The project uses Firebase Authentication and Firestore. Place your `google-services.json` file in the `app/` directory. You can generate it from the [Firebase Console](https://console.firebase.google.com/) after creating an Android app with the same package name. Enable Email/Password, Google, and Facebook sign-in methods as needed.

**OpenWeather** — Sign up at [openweathermap.org](https://openweathermap.org/api) for a free API key if you want the weather widget on the home screen.

**Facebook Login** — If you plan to use Facebook SSO, create an app on [developers.facebook.com](https://developers.facebook.com/) and add the App ID and Client Token to `local.properties`.

### Run

Connect a device or start an emulator, then click **Run** in Android Studio or use:

```bash
./gradlew installDebug
```

On first launch, the app seeds Firestore with sample place data if the collection is empty. Make sure your Firebase project has Firestore enabled and the appropriate security rules for development.

## Screenshots

<img width="216" height="465" alt="image" src="https://github.com/user-attachments/assets/5e8e4d52-3569-48d5-956c-1a08894ff4d9" />
<img width="220" height="473" alt="image" src="https://github.com/user-attachments/assets/acc30f66-dc40-40d9-8340-45cc2196bd48" />
<img width="209" height="454" alt="image" src="https://github.com/user-attachments/assets/147cbe8e-92c0-4925-b33d-663bf6ef4465" />
<img width="222" height="462" alt="image" src="https://github.com/user-attachments/assets/5d55afa7-ba30-4f3e-b115-405a1fe44bf4" />
<img width="212" height="455" alt="image" src="https://github.com/user-attachments/assets/fc422c0e-b0da-4537-832b-8cd1ddebf243" />
<img width="209" height="447" alt="image" src="https://github.com/user-attachments/assets/596ce71c-f9c1-41b0-b477-dd89a0c1819f" />


## License

This is an academic project developed for Université Abdelmalek Essaâdi, Faculté Polydisciplinaire de Larache, academic year 2025/2026. It is not licensed for commercial use.
