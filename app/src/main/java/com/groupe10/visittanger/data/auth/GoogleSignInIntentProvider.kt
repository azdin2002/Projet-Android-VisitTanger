package com.groupe10.visittanger.data.auth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.groupe10.visittanger.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Construit l’intent Google Sign-In avec le Web Client ID fusionné par le plugin
 * `google-services` ([R.string.default_web_client_id], issu de oauth_client client_type 3).
 */
@Singleton
class GoogleSignInIntentProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val googleSignInOptions: GoogleSignInOptions by lazy {
        val webClientId = context.getString(R.string.default_web_client_id)
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()
    }

    fun getSignInIntent(): Intent =
        GoogleSignIn.getClient(context, googleSignInOptions).signInIntent
}
