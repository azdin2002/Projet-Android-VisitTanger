package com.groupe10.visittanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.groupe10.visittanger.ui.main.MainScreen
import com.groupe10.visittanger.ui.theme.VisitTangerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VisitTangerTheme {
                MainScreen()
            }
        }
    }
}