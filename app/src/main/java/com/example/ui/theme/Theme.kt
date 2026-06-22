package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PremiumGold,
    secondary = TechCyan,
    tertiary = PremiumRed,
    background = SlateDark,
    surface = CardGrey,
    onPrimary = SlateDark,
    onSecondary = SlateDark,
    onTertiary = TextWhite,
    onBackground = TextWhite,
    onSurface = TextWhite,
    surfaceVariant = BorderGrey,
    onSurfaceVariant = TextGrey
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    // We strictly force a sleek premium dark technical theme to match elite taxi/engineering interfaces 
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
