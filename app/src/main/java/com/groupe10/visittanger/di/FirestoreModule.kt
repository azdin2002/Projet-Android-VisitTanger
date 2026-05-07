package com.groupe10.visittanger.di

import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.groupe10.visittanger.data.remote.FirestoreSeeder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore =
        FirebaseFirestore.getInstance(Firebase.app, "places")

    @Provides
    @Singleton
    fun provideFirestoreSeeder(
        firestore: FirebaseFirestore
    ): FirestoreSeeder = FirestoreSeeder(firestore)
}