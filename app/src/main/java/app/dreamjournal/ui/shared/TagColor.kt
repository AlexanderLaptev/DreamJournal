package app.dreamjournal.ui.shared

import androidx.compose.ui.graphics.Color
import app.dreamjournal.model.TagColor
import app.dreamjournal.ui.theme.CatppuccinColorScheme

fun TagColor.getColor(scheme: CatppuccinColorScheme): Color = when (this) {
    TagColor.White -> scheme.text
    TagColor.Red -> scheme.red
    TagColor.Orange -> scheme.peach
    TagColor.Yellow -> scheme.yellow
    TagColor.Green -> scheme.green
    TagColor.Teal -> scheme.teal
    TagColor.Blue -> scheme.blue
    TagColor.Purple -> scheme.mauve
}
