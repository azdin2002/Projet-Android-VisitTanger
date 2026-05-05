package com.groupe10.visittanger.ui.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.groupe10.visittanger.data.auth.GoogleSignInIntentProvider
import com.groupe10.visittanger.domain.model.User
import com.groupe10.visittanger.domain.repository.AuthRepository
import com.groupe10.visittanger.domain.usecase.LoginUseCase
import com.groupe10.visittanger.domain.usecase.LogoutUseCase
import com.groupe10.visittanger.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val user: User? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val googleSignInIntentProvider: GoogleSignInIntentProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun getGoogleSignInIntent(): Intent {
        Log.d(TAG_GOOGLE, "Construction / lancement préparation intent Google Sign-In")
        return googleSignInIntentProvider.getSignInIntent()
    }

    /**
     * Traite le résultat de [ActivityResultContracts.StartActivityForResult] pour Google.
     */
    fun handleGoogleSignInResult(result: ActivityResult) {
        Log.d(
            TAG_GOOGLE,
            "Retour ActivityResult: resultCode=${result.resultCode} (RESULT_OK=${Activity.RESULT_OK}), hasData=${result.data != null}",
        )
        if (result.resultCode != Activity.RESULT_OK) {
            Log.d(TAG_GOOGLE, "Flux Google interrompu (annulation ou erreur activité)")
            _uiState.update { it.copy(isLoading = false, error = "Connexion Google annulée.") }
            return
        }
        val data = result.data
        if (data == null) {
            Log.w(TAG_GOOGLE, "Intent résultat sans extras (data == null)")
            _uiState.update { it.copy(isLoading = false, error = "Réponse Google invalide.") }
            return
        }
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            Log.d(
                TAG_GOOGLE,
                "Compte Google: email=${account.email}, displayName=${account.displayName}, idToken présent=${!idToken.isNullOrBlank()}",
            )
            if (idToken.isNullOrBlank()) {
                Log.e(
                    TAG_GOOGLE,
                    "idToken absent — vérifier Web Client ID (requestIdToken) et google-services.json (oauth client type 3)",
                )
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Jeton Google manquant. Vérifiez la config Firebase (Web Client ID / SHA-1).",
                    )
                }
                return
            }
            Log.d(TAG_GOOGLE, "idToken reçu, longueur=${idToken.length}, appel Firebase signInWithCredential")
            loginWithGoogle(idToken)
        } catch (e: ApiException) {
            Log.e(TAG_GOOGLE, "ApiException statusCode=${e.statusCode} message=${e.message}", e)
            _uiState.update {
                it.copy(isLoading = false, error = e.message ?: "Échec Google Sign-In (${e.statusCode})")
            }
        }
    }

    fun isUserLoggedIn(): Boolean = authRepository.isUserLoggedIn()

    fun loginWithEmail(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = loginUseCase(email, password)
            result.fold(
                onSuccess = { user ->
                    _uiState.update { it.copy(isLoading = false, isSuccess = true, user = user) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }

    fun registerWithEmail(email: String, password: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = registerUseCase(email, password, name)
            result.fold(
                onSuccess = { user ->
                    _uiState.update { it.copy(isLoading = false, isSuccess = true, user = user) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG_GOOGLE, "loginWithGoogle: début coroutine IO, appel repository")
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = authRepository.loginWithGoogle(idToken)
            result.fold(
                onSuccess = { user ->
                    Log.d(TAG_GOOGLE, "loginWithGoogle: succès Firebase uid=${user.uid}")
                    _uiState.update { it.copy(isLoading = false, isSuccess = true, user = user) }
                },
                onFailure = { error ->
                    Log.e(TAG_GOOGLE, "loginWithGoogle: échec repository", error)
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }

    fun loginWithFacebook(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = authRepository.loginWithFacebook(token)
            result.fold(
                onSuccess = { user ->
                    _uiState.update { it.copy(isLoading = false, isSuccess = true, user = user) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase()
            _uiState.update { AuthUiState() }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    /**
     * Réinitialise l’UI d’auth (ex. isSuccess) après déconnexion depuis le profil.
     * Sans cela, [LoginScreen] voit encore isSuccess == true et renvoie tout de suite vers Home.
     */
    fun resetAuthUiState() {
        _uiState.value = AuthUiState()
    }

    private companion object {
        const val TAG_GOOGLE = "VisitTanger.GoogleSignIn"
    }
}
