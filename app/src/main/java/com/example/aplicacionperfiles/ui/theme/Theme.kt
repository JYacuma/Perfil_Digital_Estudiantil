package com.example.aplicacionperfiles.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DeepBlack = Color(0xFF121212)
val CyanNeon = Color(0xFF00E5FF)

private val AppColorScheme = darkColorScheme(
    primary = CyanNeon,
    background = DeepBlack,
    surface = DeepBlack,
    onPrimary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun AplicacionPerfilesTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}