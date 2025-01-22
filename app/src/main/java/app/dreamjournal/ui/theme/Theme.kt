package app.dreamjournal.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = mocha_mauve,
    onPrimary = mocha_base,
    secondary = mocha_mauve,
    onSecondary = mocha_base,
    tertiary = mocha_mauve,
    onTertiary = mocha_base,
    surface = mocha_base,
    surfaceVariant = mocha_base,
    onSurface = mocha_text,
    onSurfaceVariant = mocha_overlay2,
    background = mocha_base,
    onBackground = mocha_text,
    surfaceContainerHigh = mocha_surface0,
    outline = mocha_overlay2,
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    val colors = DarkColors
    MaterialTheme(colorScheme = colors, content = content)
}
