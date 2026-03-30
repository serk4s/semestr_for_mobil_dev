package com.example.recipebook.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF800020),
    secondary = Color(0xFFE6E6E6),
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color.Black
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF800020),
    secondary = Color(0xFF4A0000),
    background = Color(0xFF0A0A0A),
    surface = Color(0xFF1A1A1A),
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onSecondary = Color.White
)