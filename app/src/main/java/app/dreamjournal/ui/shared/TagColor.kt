package app.dreamjournal.ui.shared

import androidx.compose.ui.graphics.Color
import app.dreamjournal.ui.theme.CatppuccinColorScheme

enum class TagColor {
    White,
    Red,
    Orange,
    Yellow,
    Green,
    Teal,
    Blue,
    Purple;

    fun getColor(scheme: CatppuccinColorScheme): Color = when (this) {
        White -> scheme.text
        Red -> scheme.red
        Orange -> scheme.peach
        Yellow -> scheme.yellow
        Green -> scheme.green
        Teal -> scheme.teal
        Blue -> scheme.blue
        Purple -> scheme.mauve
    }
}
