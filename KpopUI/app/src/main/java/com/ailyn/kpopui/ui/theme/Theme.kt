package com.ailyn.kpopui.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val KpopColorScheme = lightColorScheme(
    primary          = KpopPurple,
    onPrimary        = KpopOnPrimary,
    primaryContainer = KpopPurpleLight,
    secondary        = KpopPurpleDark,
    background       = KpopBackground,
    surface          = KpopSurface,
    onBackground     = KpopPurpleDark,
)

@Composable
fun KpopUITheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KpopColorScheme,
        typography  = Typography,
        content     = content
    )
}
