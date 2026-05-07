import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    // Kotlin is built-in with AGP 9.2.0, no need for org.jetbrains.kotlin.android
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}


val props = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

val mapsApiKey: String = props.getProperty("MAPS_API_KEY")
    ?: project.findProperty("MAPS_API_KEY") as String?
    ?: throw GradleException("MAPS_API_KEY not found in local.properties")

val facebookAppId: String = props.getProperty("FACEBOOK_APP_ID")
    ?: project.findProperty("FACEBOOK_APP_ID") as String?
    ?: "0"

val facebookClientToken: String = props.getProperty("FACEBOOK_CLIENT_TOKEN")
    ?: project.findProperty("FACEBOOK_CLIENT_TOKEN") as String?
    ?: ""

val weatherApiKey: String = props.getProperty("OPENWEATHER_API_KEY")
    ?: project.findProperty("OPENWEATHER_API_KEY") as String?
    ?: ""

android {
    namespace = "com.groupe10.visittanger"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.groupe10.visittanger"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
        manifestPlaceholders["FACEBOOK_APP_ID"] = facebookAppId
        manifestPlaceholders["FACEBOOK_CLIENT_TOKEN"] = facebookClientToken
        manifestPlaceholders["FB_LOGIN_PROTOCOL_SCHEME"] = "fb$facebookAppId"

        buildConfigField("String", "WEATHER_API_KEY", "\"$weatherApiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // Compose BOM (une seule fois)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation("androidx.compose.material:material-icons-extended")
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.59.2")
    ksp("com.google.dagger:hilt-compiler:2.59.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Room
    implementation("androidx.room:room-runtime:2.8.4")
    implementation("androidx.room:room-ktx:2.8.4")
    ksp("androidx.room:room-compiler:2.8.4")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Adaptive UI
    implementation("androidx.compose.material3:material3-window-size-class:1.3.1")
    implementation("androidx.window:window:1.3.0")

    // Google Maps
    implementation("com.google.maps.android:maps-compose:4.3.3")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.1")
    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:21.1.0")

    // Facebook
    implementation("com.facebook.android:facebook-login:16.3.0")

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}