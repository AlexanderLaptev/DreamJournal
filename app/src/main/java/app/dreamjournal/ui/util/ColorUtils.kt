package app.dreamjournal.ui.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

fun Color.blend(other: Color, alpha: Float = 0.5f) =
    Color(ColorUtils.blendARGB(this.toArgb(), other.toArgb(), alpha))
