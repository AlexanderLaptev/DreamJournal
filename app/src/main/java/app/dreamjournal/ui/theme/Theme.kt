package app.dreamjournal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun DreamJournalTheme(
    appThemePreference: ThemePreference = ThemePreference.System,
    content: @Composable () -> Unit,
) {
    val useDarkThemePreference = when (appThemePreference) {
        ThemePreference.Light -> false
        ThemePreference.Dark -> true
        ThemePreference.System -> isSystemInDarkTheme()
    }
    val catppuccinColorScheme = if (useDarkThemePreference) MochaColorScheme else LatteColorScheme

    CompositionLocalProvider(
        LocalCatppuccinColorScheme provides catppuccinColorScheme
    ) {
        val colors = if (useDarkThemePreference) darkColorScheme(
            primary = CatppuccinColors.mauve,
            onPrimary = CatppuccinColors.base,
            primaryContainer = CatppuccinColors.mauve,
            secondary = CatppuccinColors.mauve,
            onSecondary = CatppuccinColors.base,
            secondaryContainer = CatppuccinColors.mauve,
            onSecondaryContainer = CatppuccinColors.base,
            tertiary = CatppuccinColors.mauve,
            onTertiary = CatppuccinColors.base,
            surface = CatppuccinColors.base,
            surfaceVariant = CatppuccinColors.base,
            onSurface = CatppuccinColors.text,
            onSurfaceVariant = CatppuccinColors.overlay2,
            background = CatppuccinColors.base,
            onBackground = CatppuccinColors.text,
            surfaceContainer = CatppuccinColors.surface0,
            surfaceContainerHigh = CatppuccinColors.surface0,
            outline = CatppuccinColors.overlay2,
        ) else lightColorScheme(
            primary = CatppuccinColors.mauve,
            onPrimary = CatppuccinColors.base,
            primaryContainer = CatppuccinColors.mauve,
            secondary = CatppuccinColors.mauve,
            onSecondary = CatppuccinColors.base,
            secondaryContainer = CatppuccinColors.mauve,
            onSecondaryContainer = CatppuccinColors.base,
            tertiary = CatppuccinColors.mauve,
            onTertiary = CatppuccinColors.base,
            surface = CatppuccinColors.base,
            surfaceVariant = CatppuccinColors.base,
            onSurface = CatppuccinColors.text,
            onSurfaceVariant = CatppuccinColors.overlay2,
            background = CatppuccinColors.base,
            onBackground = CatppuccinColors.text,
            surfaceContainer = CatppuccinColors.surface0,
            surfaceContainerHigh = CatppuccinColors.surface0,
            outline = CatppuccinColors.overlay2,
        )
        MaterialTheme(colorScheme = colors, content = content)
    }
}

val LocalThemePreferenceProvider = staticCompositionLocalOf { ThemePreference.System }
val LocalThemeChangeProvider = staticCompositionLocalOf<(ThemePreference) -> Unit> {
    error("onThemeChange is not provided")
}

enum class ThemePreference {
    Light, Dark, System
}
