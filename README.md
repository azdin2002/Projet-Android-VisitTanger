# 🕌 Visit Tanger

> Application mobile touristique Android dédiée à la découverte de Tanger — destinations, attractions, activités locales, cartes interactives et itinéraires optimisés.

---

## 👥 Membres de l'équipe

| Nom | Rôle |
|-----|------|
| **Wail CHAIRI MAHJOR** | UI / Jetpack Compose / Écrans |
| **Azzeddine SALMOUN** | Data / API / ViewModel / Room |

**Groupe 10** — Master DevOps et Cloud Computing  
Université Abdelmalek Essaâdi — Faculté Polydisciplinaire de Larache  
Année Universitaire : 2025/2026  
Encadrant : **Pr. KOUISSI Mohamed**

---

## 📱 Description du projet

**Visit Tanger** est une application Android native permettant aux touristes et habitants de découvrir la ville de Tanger sous toutes ses facettes :

- 🕌 Sites historiques (Kasbah, Médina, Dar el Makhzen...)
- 🌊 Nature et paysages (Cap Spartel, Grottes d'Hercule, plages...)
- 🍽️ Gastronomie locale (restaurants, cafés, spécialités)
- 🛍️ Shopping (souks, boutiques, artisanat)
- 🗓️ Événements culturels et locaux
- 🗺️ Itinéraires optimisés (1 jour, week-end, thématiques)

---

## ✨ Fonctionnalités principales

### 🔐 Authentification
- Inscription / Connexion classique (email + mot de passe)
- Single Sign-On (SSO) via **Google** et **Facebook**
- Gestion du profil utilisateur

### 🏠 Accueil
- Bannière de bienvenue avec mise en avant des incontournables
- Filtres par catégorie (Histoire, Nature, Food, Shopping, Événements)
- Barre de recherche intelligente
- Météo locale en temps réel

### 🗺️ Carte interactive
- Carte Google Maps avec pins colorés par catégorie
- Recherche de lieux directement sur la carte
- Fiche rapide d'un lieu en bottom sheet
- Calcul d'itinéraire vers un lieu

### 📍 Détail d'un lieu
- Galerie photos
- Description multilingue (FR / EN / AR)
- Horaires d'ouverture, tarifs, coordonnées
- Avis et notes des utilisateurs
- Ajout aux favoris
- Bouton "Itinéraire" (intégration Maps)

### 🗓️ Itinéraires
- Templates prédéfinis : "1 jour à Tanger", "Week-end complet", "Tanger historique", "Nature et évasion"
- Création d'un itinéraire personnalisé
- Export et partage d'un itinéraire

### ❤️ Favoris
- Sauvegarde locale des lieux favoris (Room — mode hors ligne)
- Tri et filtres des favoris

### 🌍 Internationalisation
- Français 🇫🇷
- Anglais 🇬🇧
- Arabe 🇲🇦 (support RTL natif)

### 📐 Interface adaptative
- Support Smartphone et Tablette
- Modes Portrait et Paysage
- Design System unifié (couleurs, typographie, composants)

---

## 🏗️ Architecture du projet

L'application suit l'architecture **MVVM (Model-View-ViewModel)** recommandée par Google pour Android.

```
app/
├── data/
│   ├── remote/
│   │   ├── api/           ← Retrofit services (PlaceApiService, AuthApiService)
│   │   └── dto/           ← Data Transfer Objects
│   ├── local/
│   │   ├── dao/           ← Room DAOs (PlaceDao, FavoriteDao)
│   │   ├── entity/        ← Room Entities
│   │   └── AppDatabase.kt
│   ├── repository/        ← Implémentations des repositories
│   └── datastore/         ← DataStore (langue, préférences)
│
├── domain/
│   ├── model/             ← Modèles métier (Place, Itinerary, User, Review)
│   ├── repository/        ← Interfaces des repositories
│   └── usecase/           ← Use Cases (GetPlacesUseCase, SaveFavoriteUseCase...)
│
├── ui/
│   ├── auth/              ← LoginScreen, RegisterScreen
│   ├── home/              ← HomeScreen, HomeViewModel
│   ├── map/               ← MapScreen, MapViewModel
│   ├── detail/            ← DetailScreen, PlaceViewModel
│   ├── itinerary/         ← ItineraryScreen, ItineraryViewModel
│   ├── favorites/         ← FavoritesScreen, FavoritesViewModel
│   ├── profile/           ← ProfileScreen, ProfileViewModel
│   └── theme/             ← Design System (Color, Typography, Theme)
│
└── di/                    ← Hilt Modules (NetworkModule, DatabaseModule...)
```

### Schéma d'architecture

```
┌─────────────────────────────────────┐
│           UI Layer (Compose)         │
│  Screens ←→ ViewModels (StateFlow)  │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│         Domain Layer                 │
│     Use Cases + Models               │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│          Data Layer                  │
│  Remote (Retrofit) | Local (Room)    │
│  DataStore | SharedPreferences       │
└─────────────────────────────────────┘
         ↑ Injection via Hilt (DI)
```

---

## 🛠️ Technologies utilisées

| Couche | Technologie |
|--------|------------|
| Langage | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Navigation | Navigation Compose |
| ViewModel | ViewModel + StateFlow + Coroutines |
| DI | Hilt |
| Base de données locale | Room |
| Préférences | DataStore + SharedPreferences |
| Réseau | Retrofit + OkHttp + Gson |
| Images | Coil |
| Carte | Google Maps SDK for Android |
| Auth | Firebase Authentication |
| SSO | Google Sign-In + Facebook SDK |
| Tests | JUnit + Mockk |
| Versioning | Git + GitHub |

---

## ⚙️ Dépendances à télécharger


---


## 📄 Licence

Projet académique — Université Abdelmalek Essaâdi 2025/2026
