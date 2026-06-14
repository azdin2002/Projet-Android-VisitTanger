package com.groupe10.visittanger.ui.auth

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.groupe10.visittanger.R
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.groupe10.visittanger.ui.navigation.Screen
import com.groupe10.visittanger.ui.theme.*

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess && viewModel.isUserLoggedIn()) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Register.route) { inclusive = true }
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
        // Subtle orange tint gradient on top
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
                .align(Alignment.TopStart)
                .offset(x = (-50).dp, y = (-50).dp)
                .size(300.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(StitchPrimary.copy(alpha = 0.12f), Color.Transparent)
                    ),
                    CircleShape
                )
                .blur(60.dp)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 50.dp, y = 50.dp)
                .size(250.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(StitchSecondary.copy(alpha = 0.1f), Color.Transparent)
                    ),
                    CircleShape
                )
                .blur(60.dp)
        )

        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 4.dp, vertical = 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
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

                    IconButton(onClick = themeViewModel::toggleDarkMode) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Dark Mode",
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
                verticalArrangement = Arrangement.Center
            ) {
                // Header (Condensed)
                Text(
                    text = stringResource(R.string.brand_tangier),
                    style = MaterialTheme.typography.displayLarge.copy(
                        color = StitchPrimary,
                        fontSize = 48.sp,
                        fontFamily = TangerDisplayFont,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = stringResource(R.string.auth_create_account),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = StitchOnSurface,
                        fontFamily = TangerSerifFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = stringResource(R.string.auth_join_community),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = StitchOnSurface.copy(alpha = 0.6f),
                        fontFamily = TangerSerifFont
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
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Full Name
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = stringResource(R.string.auth_full_name),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = StitchOnSurface.copy(alpha = 0.7f),
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                placeholder = { Text(stringResource(R.string.auth_name_placeholder), color = Color.Gray.copy(alpha = 0.4f)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = StitchPrimary,
                                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.2f),
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                                    focusedContainerColor = Color.White
                                ),
                                singleLine = true
                            )
                        }

                        // Email
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = stringResource(R.string.auth_email),
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
                            Text(
                                text = stringResource(R.string.auth_password),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = StitchOnSurface.copy(alpha = 0.7f),
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
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

                        // Confirm Password
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = stringResource(R.string.auth_confirm_password),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = StitchOnSurface.copy(alpha = 0.7f),
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            OutlinedTextField(
                                value = confirmPassword,
                                onValueChange = { confirmPassword = it },
                                placeholder = { Text("••••••••", color = Color.Gray.copy(alpha = 0.4f)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                visualTransformation = PasswordVisualTransformation(),
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

                        // Sign Up Button
                        Button(
                            onClick = {
                                if (password == confirmPassword) {
                                    viewModel.registerWithEmail(email, password, name)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StitchPrimary),
                            shape = RoundedCornerShape(10.dp),
                            enabled = !uiState.isLoading
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                            } else {
                                Text(
                                    stringResource(R.string.auth_sign_up_button),
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                )
                            }
                        }
                    }
                }

                // Footer
                Row(modifier = Modifier.padding(top = 24.dp)) {
                    Text(
                        stringResource(R.string.auth_already_account_prompt),
                        style = MaterialTheme.typography.bodyMedium.copy(color = StitchOnSurface.copy(alpha = 0.6f))
                    )
                    Text(
                        stringResource(R.string.auth_login),
                        color = StitchSecondary,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Login.route)
                        }
                    )
                }
            }
        }
    }
}
