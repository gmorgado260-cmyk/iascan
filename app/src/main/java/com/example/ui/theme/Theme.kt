package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = GoldPrimary,
  onPrimary = Color(0xFF140F00),
  primaryContainer = GoldContainer,
  onPrimaryContainer = GoldLight,
  secondary = SilverAccent,
  onSecondary = Color(0xFF111726),
  secondaryContainer = CardSurfaceElevated,
  onSecondaryContainer = TextWhite,
  tertiary = BronzeAccent,
  onTertiary = Color.White,
  background = ObsidianDark,
  onBackground = TextWhite,
  surface = SlateDark,
  onSurface = TextWhite,
  surfaceVariant = CardSurfaceDark,
  onSurfaceVariant = TextMuted,
  outline = BorderSubtle,
  outlineVariant = Color(0xFF1E273A),
  error = RubyAlert,
  onError = Color.White,
  errorContainer = RubyContainer,
  onErrorContainer = Color(0xFFFFB4AB)
)

private val LightColorScheme = darkColorScheme(
  // Maintain the signature dark numismatic aesthetic across all system settings for professional consistency
  primary = GoldPrimary,
  onPrimary = Color(0xFF140F00),
  primaryContainer = GoldContainer,
  onPrimaryContainer = GoldLight,
  secondary = SilverAccent,
  onSecondary = Color(0xFF111726),
  secondaryContainer = CardSurfaceElevated,
  onSecondaryContainer = TextWhite,
  tertiary = BronzeAccent,
  onTertiary = Color.White,
  background = ObsidianDark,
  onBackground = TextWhite,
  surface = SlateDark,
  onSurface = TextWhite,
  surfaceVariant = CardSurfaceDark,
  onSurfaceVariant = TextMuted,
  outline = BorderSubtle,
  outlineVariant = Color(0xFF1E273A),
  error = RubyAlert,
  onError = Color.White
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Preserve brand identity
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

