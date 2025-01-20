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
    onSurface = mocha_text,
    background = mocha_base,
    onBackground = mocha_text,
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    val colors = DarkColors
    MaterialTheme(colorScheme = colors, content = content)
}
