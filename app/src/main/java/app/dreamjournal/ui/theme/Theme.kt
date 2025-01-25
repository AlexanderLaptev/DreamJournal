package app.dreamjournal.ui.theme

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


@Composable
fun ApplicationTheme(
    appTheme: Theme = Theme.System,
    content: @Composable () -> Unit,
) {
    val useDarkTheme = when (appTheme) {
        Theme.Light -> false
        Theme.Dark -> true
        Theme.System -> isSystemInDarkTheme()
    }
    Log.d(null, "appTheme = $appTheme; useDarkTheme = $useDarkTheme")

    val catppuccinColorScheme = if (useDarkTheme) MochaColorScheme else LatteColorScheme

    CompositionLocalProvider(
        LocalCatppuccinColorScheme provides catppuccinColorScheme
    ) {
        val colors = if (useDarkTheme) darkColorScheme(
            primary = CatppuccinColors.mauve,
            onPrimary = CatppuccinColors.base,
            primaryContainer = CatppuccinColors.mauve,
            secondary = CatppuccinColors.mauve,
            onSecondary = CatppuccinColors.base,
            secondaryContainer = CatppuccinColors.mauve,
            onSecondaryContainer = CatppuccinColors.text,
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
            onSecondaryContainer = CatppuccinColors.text,
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

val LocalThemeProvider = staticCompositionLocalOf { Theme.System }

enum class Theme {
    Light, Dark, System
}
