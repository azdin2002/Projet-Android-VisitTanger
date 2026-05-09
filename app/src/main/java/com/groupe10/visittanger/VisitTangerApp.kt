package com.groupe10.visittanger

import android.app.Application
import com.facebook.FacebookSdk
import com.groupe10.visittanger.data.remote.FirestoreSeeder
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class VisitTangerApp : Application() {
    @Inject
    lateinit var firestoreSeeder: FirestoreSeeder

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(this)
        applicationScope.launch {
            firestoreSeeder.seedPlacesIfEmpty()
        }
    }
}