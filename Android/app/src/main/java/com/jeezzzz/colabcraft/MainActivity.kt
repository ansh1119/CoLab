package com.jeezzzz.colabcraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jeezzzz.colabcraft.ui.MainScreen
import com.jeezzzz.colabcraft.ui.theme.CoLabCraftTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoLabCraftTheme {
                MainScreen()
            }
        }
    }
}