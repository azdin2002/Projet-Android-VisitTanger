package com.groupe10.visittanger.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.groupe10.visittanger.data.local.dao.FavoriteDao
import com.groupe10.visittanger.domain.model.User
import com.groupe10.visittanger.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val favoriteDao: FavoriteDao,
) : UserRepository {

    override fun getCurrentUser(): Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            val user = firebaseUser?.let {
                User(
                    uid = it.uid,
                    email = it.email ?: "",
                    displayName = it.displayName ?: "",
                    photoUrl = it.photoUrl?.toString(),
                )
            }
            trySend(user)
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    override suspend fun updateDisplayName(name: String): Result<Unit> = runCatching {
        val user = firebaseAuth.currentUser ?: throw Exception("User not logged in")
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        user.updateProfile(profileUpdates).await()
    }

    override suspend fun updatePhotoUrl(url: String): Result<Unit> = runCatching {
        val user = firebaseAuth.currentUser ?: throw Exception("User not logged in")
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(Uri.parse(url))
            .build()
        user.updateProfile(profileUpdates).await()
    }

    override suspend fun logout(): Result<Unit> = runCatching {
        firebaseAuth.signOut()
    }

    override fun getFavoritesCount(): Flow<Int> =
        getCurrentUser().flatMapLatest { user ->
            val uid = user?.uid.orEmpty()
            if (uid.isBlank()) flowOf(0)
            else favoriteDao.getFavoritesCount(uid)
        }
}
