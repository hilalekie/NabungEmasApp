package com.example.nabungemas.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Gold300,
    onPrimary = Neutral900,
    secondary = Gold200,
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = DarkOnSurface,
    onSurface = DarkOnSurface,
    error = Error500,
    onError = Neutral100,
    outline = Neutral700,
    surfaceVariant = Neutral800,
    onSurfaceVariant = Gold100
)

private val LightColorScheme = lightColorScheme(
    primary = Gold400,
    onPrimary = Neutral900,
    secondary = Gold300,
    background = LightBackground,
    surface = LightSurface,
    onBackground = LightOnSurface,
    onSurface = LightOnSurface,
    error = Error500,
    onError = Neutral050,
    outline = Neutral200,
    surfaceVariant = Neutral100,
    onSurfaceVariant = Neutral900
)

@Composable
fun NabungEmasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = android.graphics.Color.TRANSPARENT
                window.navigationBarColor = android.graphics.Color.TRANSPARENT
                
                val windowInsetsController = WindowCompat.getInsetsController(window, view)
                // Set system status bar icons dark if in light mode, light if in dark mode
                windowInsetsController.isAppearanceLightStatusBars = !darkTheme
                windowInsetsController.isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = NabungEmasTypography,
        content = content
    )
}
