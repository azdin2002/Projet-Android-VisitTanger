package com.groupe10.visittanger.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.groupe10.visittanger.data.local.dao.FavoriteDao
import com.groupe10.visittanger.domain.model.User
import com.groupe10.visittanger.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val favoriteDao: FavoriteDao,
) : UserRepository {

    /** Émis après updateProfile + reload : les listeners Auth ne republient pas toujours le displayName. */
    private val profileRefresh = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    private fun toUser(firebaseUser: FirebaseUser?) = firebaseUser?.let {
        User(
            uid = it.uid,
            email = it.email ?: "",
            displayName = it.displayName ?: "",
            photoUrl = it.photoUrl?.toString(),
        )
    }

    override fun getCurrentUser(): Flow<User?> = merge(
        callbackFlow {
            val authListener = FirebaseAuth.AuthStateListener { auth ->
                trySend(toUser(auth.currentUser))
            }
            val idTokenListener = FirebaseAuth.IdTokenListener {
                trySend(toUser(firebaseAuth.currentUser))
            }
            firebaseAuth.addAuthStateListener(authListener)
            firebaseAuth.addIdTokenListener(idTokenListener)
            trySend(toUser(firebaseAuth.currentUser))
            awaitClose {
                firebaseAuth.removeAuthStateListener(authListener)
                firebaseAuth.removeIdTokenListener(idTokenListener)
            }
        },
        profileRefresh.map { toUser(firebaseAuth.currentUser) },
    )

    override suspend fun updateDisplayName(name: String): Result<Unit> = runCatching {
        val user = firebaseAuth.currentUser ?: throw Exception("User not logged in")
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        user.updateProfile(profileUpdates).await()
        user.reload().await()
        profileRefresh.emit(Unit)
        Log.d(TAG_PROFILE, "updateDisplayName: Firebase profile + reload OK name=$name (profileRefresh emitted)")
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

    private companion object {
        const val TAG_PROFILE = "VisitTanger.Profile"
    }
}
