package app.dreamjournal.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private typealias CatppuccinColor = com.catppuccin.Color

fun CatppuccinColor.toCompose(): Color {
    // FIXME: bug in Catppuccin#29, green and blue are swapped
    return Color(red = this.r(), green = this.b(), blue = this.g())
}

val AccentColor @Composable get() = CatppuccinColors.mauve
val FavoriteColor @Composable get() = CatppuccinColors.yellow
val LucidColor @Composable get() = CatppuccinColors.mauve
