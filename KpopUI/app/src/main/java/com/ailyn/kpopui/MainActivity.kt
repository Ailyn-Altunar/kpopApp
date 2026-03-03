package com.ailyn.kpopui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ailyn.kpopui.core.navigation.NavigationWrapper


import com.ailyn.kpopui.ui.theme.KpopUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KpopUITheme {
                NavigationWrapper()
            }
        }
    }
}
