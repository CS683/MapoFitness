package com.example.mapofitness.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val DarkColorPalette = darkColorScheme(
    primary = SkyBlue,
    onPrimary = Dark,
    background = Dark,
    onSurface = White,
    secondary = SkyBlue,
    onSecondary = DarkGray,
    onPrimaryContainer = White
)

val LightColorPalette = lightColorScheme(
    primary = White,
    secondary = SkyBlue,
    background = Dark
)

@Composable
fun MapoFitnessTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colors = if (true) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}