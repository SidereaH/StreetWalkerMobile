package com.streetwalkermobile.shared.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object StreetWalkerColors {
    val primary = Color(0xFF00687A)
    val onPrimary = Color(0xFFFFFFFF)
    val primaryContainer = Color(0xFF97F0FF)
    val onPrimaryContainer = Color(0xFF001F26)
    val secondary = Color(0xFF4A6268)
    val onSecondary = Color(0xFFFFFFFF)
    val secondaryContainer = Color(0xFFCCE7EE)
    val onSecondaryContainer = Color(0xFF051F24)
    val tertiary = Color(0xFF575C7E)
    val onTertiary = Color(0xFFFFFFFF)
    val tertiaryContainer = Color(0xFFDCE1FF)
    val onTertiaryContainer = Color(0xFF121940)
    val error = Color(0xFFBA1A1A)
    val onError = Color(0xFFFFFFFF)
    val errorContainer = Color(0xFFFFDAD6)
    val onErrorContainer = Color(0xFF410002)
    val background = Color(0xFFFAFDFC)
    val onBackground = Color(0xFF191C1D)
    val surface = Color(0xFFFAFDFC)
    val onSurface = Color(0xFF191C1D)
    val surfaceVariant = Color(0xFFDBE4E6)
    val onSurfaceVariant = Color(0xFF40484A)
    val outline = Color(0xFF707879)
}

val LightColorScheme: ColorScheme = lightColorScheme(
    primary = StreetWalkerColors.primary,
    onPrimary = StreetWalkerColors.onPrimary,
    primaryContainer = StreetWalkerColors.primaryContainer,
    onPrimaryContainer = StreetWalkerColors.onPrimaryContainer,
    secondary = StreetWalkerColors.secondary,
    onSecondary = StreetWalkerColors.onSecondary,
    secondaryContainer = StreetWalkerColors.secondaryContainer,
    onSecondaryContainer = StreetWalkerColors.onSecondaryContainer,
    tertiary = StreetWalkerColors.tertiary,
    onTertiary = StreetWalkerColors.onTertiary,
    tertiaryContainer = StreetWalkerColors.tertiaryContainer,
    onTertiaryContainer = StreetWalkerColors.onTertiaryContainer,
    error = StreetWalkerColors.error,
    onError = StreetWalkerColors.onError,
    errorContainer = StreetWalkerColors.errorContainer,
    onErrorContainer = StreetWalkerColors.onErrorContainer,
    background = StreetWalkerColors.background,
    onBackground = StreetWalkerColors.onBackground,
    surface = StreetWalkerColors.surface,
    onSurface = StreetWalkerColors.onSurface,
    surfaceVariant = StreetWalkerColors.surfaceVariant,
    onSurfaceVariant = StreetWalkerColors.onSurfaceVariant,
    outline = StreetWalkerColors.outline
)

val DarkColorScheme: ColorScheme = darkColorScheme(
    primary = Color(0xFF4BD8F6),
    onPrimary = StreetWalkerColors.onPrimaryContainer,
    primaryContainer = Color(0xFF004E5E),
    onPrimaryContainer = StreetWalkerColors.primaryContainer,
    secondary = Color(0xFFB0CBD2),
    onSecondary = StreetWalkerColors.onSecondaryContainer,
    secondaryContainer = Color(0xFF324B51),
    onSecondaryContainer = StreetWalkerColors.secondaryContainer,
    tertiary = Color(0xFFBEC5EB),
    onTertiary = StreetWalkerColors.onTertiaryContainer,
    tertiaryContainer = Color(0xFF3E4465),
    onTertiaryContainer = StreetWalkerColors.tertiaryContainer,
    error = StreetWalkerColors.error,
    onError = StreetWalkerColors.onError,
    errorContainer = Color(0xFF93000A),
    onErrorContainer = StreetWalkerColors.errorContainer,
    background = Color(0xFF191C1D),
    onBackground = StreetWalkerColors.background,
    surface = Color(0xFF191C1D),
    onSurface = StreetWalkerColors.background,
    surfaceVariant = Color(0xFF40484A),
    onSurfaceVariant = StreetWalkerColors.surfaceVariant,
    outline = Color(0xFF8A9294)
)
