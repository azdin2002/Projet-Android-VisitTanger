# Visit Tanger

Visit Tanger est une application Android conçue pour aider les touristes et les habitants à explorer la ville de Tanger. Vous pouvez parcourir les lieux d'intérêt, les visualiser sur une carte, enregistrer des favoris, suivre des itinéraires suggérés et basculer entre le français, l'anglais et l'arabe.

Ce projet a été développé dans le cadre du Master DevOps & Cloud Computing à l'Université Abdelmalek Essaâdi — Faculté Polydisciplinaire de Larache (2025/2026), sous la supervision du Pr. KOUISSI Mohamed.

---

## Équipe

**Groupe 10**

| Membre | Rôle |
|--------|------|
| Wail CHAIRI MAHJOR | Couche UI — écrans Jetpack Compose, navigation, système de design |
| Azzeddine SALMOUN | Couche Data — repositories, ViewModels, Room, Firebase, intégration APIs |

---

## À propos du projet

Tanger offre de nombreux atouts : des sites historiques comme la Kasbah et la Médina, des sites naturels tels que le Cap Spartel et les Grottes d'Hercule, des plages, une gastronomie locale, des marchés et des événements culturels. Visit Tanger rassemble tout cela en un seul endroit pour vous permettre de découvrir la ville à votre rythme, que vous planifiiez une excursion d'une journée ou un séjour plus long.

L'application couvre les fonctionnalités essentielles d'un guide de ville : création de compte et connexion, écran d'accueil avec recherche et filtres par catégorie, carte interactive, pages détaillées des lieux, itinéraires pré-construits, favoris hors ligne, profil utilisateur et météo en direct pour Tanger.

---

## Fonctionnalités

**Authentification** — Les utilisateurs peuvent s'inscrire et se connecter avec un email et un mot de passe. La connexion via Google et Facebook est également disponible grâce à Firebase Authentication.

**Accueil** — L'écran principal met en avant les lieux populaires, permet de filtrer par catégorie (Histoire, Nature, Gastronomie, Shopping, Événements, Plages), de rechercher par nom et affiche la météo actuelle de Tanger via l'API OpenWeather.

**Carte** — Les lieux apparaissent sur une carte Google Maps avec des marqueurs colorés par catégorie. Vous pouvez appuyer sur un marqueur pour ouvrir un aperçu rapide, puis accéder à la page de détail complète ou obtenir un itinéraire.

**Détail du lieu** — Chaque lieu dispose de photos, d'une description multilingue, des horaires d'ouverture, des tarifs si applicable, des avis et de la possibilité de l'ajouter aux favoris ou d'ouvrir la navigation dans Google Maps.

**Itinéraires** — Des parcours pré-construits sont disponibles (une journée à Tanger, week-end, visite historique, escapade nature, gastronomie…). Vous pouvez parcourir la liste des étapes de chaque itinéraire et naviguer vers n'importe quel lieu depuis là.

**Favoris** — Les lieux sauvegardés sont stockés localement avec Room et restent disponibles même sans connexion internet.

**Profil et paramètres** — Gérez votre compte, changez la langue de l'application (français, anglais ou arabe avec support RTL) et déconnectez-vous.

**Interface adaptative** — L'interface s'adapte aux téléphones et aux tablettes, en mode portrait et paysage.

---

## Technologies utilisées

L'application est entièrement développée en **Kotlin**. L'interface utilise **Jetpack Compose** avec **Material Design 3**. Nous suivons l'architecture **MVVM** avec une séparation claire entre les couches UI, Domain et Data. **Hilt** gère l'injection de dépendances. Les données locales passent par **Room** et **DataStore**. Les données distantes proviennent de **Firebase Firestore** (lieux et itinéraires) et de **Retrofit** (météo). Les images sont chargées avec **Coil**. Les cartes utilisent la bibliothèque **Google Maps Compose**. L'authentification repose sur **Firebase Auth**, avec Google Sign-In et le SDK Facebook pour la connexion sociale.

| Paramètre | Version |
|-----------|---------|
| Min SDK | 26 (Android 8.0) |
| Target SDK | 36 |
| Compile SDK | 36 |
| Kotlin | 2.2.10 |
| AGP | 9.2.1 |

---

## Architecture

Le code est organisé en trois couches indépendantes. La **couche UI** contient les écrans Compose et les ViewModels qui exposent l'état via `StateFlow`. La **couche Domain** contient les modèles métier, les interfaces des repositories et les cas d'utilisation — chaque Use Case réalise une seule action. La **couche Data** implémente ces repositories en combinant Firestore, Retrofit, Room et DataStore. Hilt relie tout cela.

```
app/src/main/java/com/groupe10/visittanger/
├── data/          APIs distantes, Firestore, Room, DataStore, implémentations des repositories
├── domain/        modèles, interfaces des repositories, cas d'utilisation (Use Cases)
├── ui/            écrans, ViewModels, navigation, thème
└── di/            modules Hilt
```

## Schéma d'architecture

<img width="1708" height="962" alt="Schéma d'architecture Visit Tanger — Clean Architecture en 3 couches" src="https://github.com/user-attachments/assets/5cbb1efd-3de0-4721-b9d5-3f12bb16ad27" />

Les données des lieux suivent une approche **offline-first** : Room met en cache ce que Firestore fournit, permettant à l'application d'afficher du contenu même lorsque le réseau est lent ou indisponible.

---

## Démarrage

### Prérequis

Vous avez besoin d'**Android Studio** (version récente, Ladybug ou plus récente recommandée) avec le SDK Android installé. Le projet cible l'API 36 et nécessite un appareil ou un émulateur fonctionnant au minimum sur l'API 26. Un JDK 11+ est utilisé pour la compilation.

### Cloner et ouvrir

```bash
git clone https://github.com/azdin2002/Projet-Android-VisitTanger.git
cd Projet-Android-VisitTanger
```

Ouvrez le dossier du projet dans Android Studio et laissez la synchronisation Gradle se terminer.

### Configuration

Avant de pouvoir compiler l'application, vous avez besoin d'un fichier `local.properties` à la racine du projet (ce fichier est dans le .gitignore et doit être créé localement). Ajoutez au minimum le chemin du SDK Android et une clé API Google Maps :

```properties
sdk.dir=C\:\\Users\\VotreNom\\AppData\\Local\\Android\\Sdk
MAPS_API_KEY=votre_cle_google_maps
```

Clés optionnelles — l'application compilera sans elles, mais certaines fonctionnalités seront limitées :

```properties
OPENWEATHER_API_KEY=votre_cle_openweather
FACEBOOK_APP_ID=votre_facebook_app_id
FACEBOOK_CLIENT_TOKEN=votre_facebook_client_token
```

**Google Maps** — Créez une clé dans la [Google Cloud Console](https://console.cloud.google.com/), activez le Maps SDK pour Android et restreignez-la au nom de package de votre application (`com.groupe10.visittanger`).

**Firebase** — Le projet utilise Firebase Authentication et Firestore. Placez votre fichier `google-services.json` dans le répertoire `app/`. Vous pouvez le générer depuis la [Console Firebase](https://console.firebase.google.com/) après avoir créé une application Android avec le même nom de package. Activez les méthodes de connexion Email/Mot de passe, Google et Facebook selon vos besoins.

**OpenWeather** — Inscrivez-vous sur [openweathermap.org](https://openweathermap.org/api) pour obtenir une clé API gratuite si vous souhaitez utiliser le widget météo sur l'écran d'accueil.

**Facebook Login** — Si vous prévoyez d'utiliser la connexion Facebook, créez une application sur [developers.facebook.com](https://developers.facebook.com/) et ajoutez l'App ID et le Client Token dans `local.properties`.

### Lancer l'application

Connectez un appareil ou démarrez un émulateur, puis cliquez sur **Run** dans Android Studio ou utilisez :

```bash
./gradlew installDebug
```

Au premier lancement, l'application alimente Firestore avec les données des lieux si la collection est vide. Assurez-vous que votre projet Firebase a Firestore activé avec les règles de sécurité appropriées pour le développement.

---

## Captures d'écran

<img width="216" height="465" alt="Écran d'accueil" src="https://github.com/user-attachments/assets/5e8e4d52-3569-48d5-956c-1a08894ff4d9" />
<img width="220" height="473" alt="Carte interactive" src="https://github.com/user-attachments/assets/acc30f66-dc40-40d9-8340-45cc2196bd48" />
<img width="209" height="454" alt="Détail d'un lieu" src="https://github.com/user-attachments/assets/147cbe8e-92c0-4925-b33d-663bf6ef4465" />
<img width="222" height="462" alt="Itinéraires" src="https://github.com/user-attachments/assets/5d55afa7-ba30-4f3e-b115-405a1fe44bf4" />
<img width="212" height="455" alt="Favoris" src="https://github.com/user-attachments/assets/fc422c0e-b0da-4537-832b-8cd1ddebf243" />
<img width="209" height="447" alt="Profil et paramètres" src="https://github.com/user-attachments/assets/596ce71c-f9c1-41b0-b477-dd89a0c1819f" />

---

## Licence

Ce projet académique a été développé pour l'Université Abdelmalek Essaâdi, Faculté Polydisciplinaire de Larache, année universitaire 2025/2026. Il n'est pas autorisé à des fins commerciales.
