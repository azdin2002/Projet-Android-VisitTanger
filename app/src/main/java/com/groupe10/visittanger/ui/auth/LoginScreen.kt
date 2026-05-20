package com.groupe10.visittanger.ui.auth

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.groupe10.visittanger.ui.navigation.Screen
import com.groupe10.visittanger.ui.theme.*

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Google Sign-In Launcher
    val googleSignInLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result -> viewModel.handleGoogleSignInResult(result) }

    // Facebook Login Handling
    val callbackManager = remember { CallbackManager.Factory.create() }
    val facebookLauncher = rememberLauncherForActivityResult(
        LoginManager.getInstance().createLogInActivityResultContract(callbackManager, null)
    ) { }

    DisposableEffect(Unit) {
        viewModel.initFacebookCallback(callbackManager)
        onDispose {
            LoginManager.getInstance().unregisterCallback(callbackManager)
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess && viewModel.isUserLoggedIn()) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(StitchBackground)
    ) {
        // Subtle orange tint gradient on top for better atmosphere/contrast
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(StitchSecondary.copy(alpha = 0.08f), Color.Transparent)
                    )
                )
        )

        // Decorative Background Elements (Radial Gradients)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 50.dp, y = (-50).dp)
                .size(300.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(StitchSecondary.copy(alpha = 0.12f), Color.Transparent)
                    ),
                    CircleShape
                )
                .blur(60.dp)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-50).dp, y = 50.dp)
                .size(250.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(StitchPrimary.copy(alpha = 0.1f), Color.Transparent)
                    ),
                    CircleShape
                )
                .blur(60.dp)
        )

        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                // Reduced top bar space
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 4.dp, vertical = 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = StitchPrimary
                        )
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Centers everything, fits in one page
            ) {
                // Header (Reduced bottom padding)
                Text(
                    text = "Tangier",
                    style = MaterialTheme.typography.displayLarge.copy(
                        color = StitchPrimary,
                        fontSize = 44.sp,
                        letterSpacing = (-1).sp
                    )
                )
                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = StitchOnSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "Experience the Bride of the North",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = StitchOnSurface.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )

                // Form Card
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(20.dp),
                            ambientColor = StitchSecondary.copy(alpha = 0.5f),
                            spotColor = StitchSecondary.copy(alpha = 0.25f)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.92f),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Email
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Email Address",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = StitchOnSurface.copy(alpha = 0.7f),
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                placeholder = { Text("marhaba@tangier.com", color = Color.Gray.copy(alpha = 0.4f)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                trailingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray.copy(alpha = 0.5f)) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = StitchPrimary,
                                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.2f),
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                                    focusedContainerColor = Color.White
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                singleLine = true
                            )
                        }

                        // Password
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Password",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = StitchOnSurface.copy(alpha = 0.7f),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Text(
                                    text = "Forgot Password?",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = StitchSecondary,
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    modifier = Modifier.clickable { /* Reset Action */ }
                                )
                            }
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                placeholder = { Text("••••••••", color = Color.Gray.copy(alpha = 0.4f)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(imageVector = image, contentDescription = null, tint = Color.Gray.copy(alpha = 0.5f))
                                    }
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = StitchPrimary,
                                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.2f),
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                                    focusedContainerColor = Color.White
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                singleLine = true
                            )
                        }

                        // Sign In Button
                        Button(
                            onClick = { viewModel.loginWithEmail(email, password) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StitchPrimary),
                            shape = RoundedCornerShape(10.dp),
                            enabled = !uiState.isLoading
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                            } else {
                                Text(
                                    "SIGN IN",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                )
                            }
                        }

                        // Divider (Compact)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.Gray.copy(alpha = 0.15f))
                            Text(
                                "Or continue with",
                                style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.Gray.copy(alpha = 0.15f))
                        }

                        // Social Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = { googleSignInLauncher.launch(viewModel.getGoogleSignInIntent()) },
                                modifier = Modifier.weight(1f).height(48.dp),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.15f)),
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    AsyncImage(
                                        model = "https://www.google.com/images/branding/googleg/1x/googleg_standard_color_128dp.png",
                                        contentDescription = "Google",
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Google", style = MaterialTheme.typography.labelMedium.copy(color = StitchOnSurface))
                                }
                            }

                            OutlinedButton(
                                onClick = { viewModel.onFacebookLoginClick(facebookLauncher) },
                                modifier = Modifier.weight(1f).height(48.dp),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.15f)),
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Facebook,
                                        contentDescription = "Facebook",
                                        tint = Color(0xFF1877F2),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Facebook", style = MaterialTheme.typography.labelMedium.copy(color = StitchOnSurface))
                                }
                            }
                        }
                    }
                }

                // Footer (Closer to card)
                Row(modifier = Modifier.padding(top = 24.dp)) {
                    Text(
                        "Don't have an account? ",
                        style = MaterialTheme.typography.bodyMedium.copy(color = StitchOnSurface.copy(alpha = 0.6f))
                    )
                    Text(
                        "Sign Up",
                        color = StitchSecondary,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Register.route)
                        }
                    )
                }
            }
        }
    }
}
