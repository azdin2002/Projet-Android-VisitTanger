package com.groupe10.visittanger.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreSeeder @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun seedPlacesIfEmpty() {
        runCatching {
            val placesRef = firestore.collection("places")
            Log.d("FirestoreSeeder", "Updating places with trilingual content...")

            val batch = firestore.batch()
            placeSeeds.forEach { seed ->
                val ref = placesRef.document(seed.id)
                batch.set(ref, seed.toFirestoreMap())
            }
            batch.commit().await()
            Log.d("FirestoreSeeder", "Places seeded with FR/EN/AR content.")
        }.onFailure { e ->
            Log.e("FirestoreSeeder", "Erreur: ${e.message}")
        }
    }
}
