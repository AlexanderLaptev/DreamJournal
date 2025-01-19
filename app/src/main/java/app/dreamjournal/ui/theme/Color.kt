package app.dreamjournal.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val TextColor = Color(0xFFCAD3F5)
val DarkText = Color(0xFF394369)

val Red = Color(0xFFED8796)
val DarkRed = Color(0xFF601C26)

val Blue = Color(0xFF8AADF4)
val DarkBlue = Color(0xFF122D65)

fun Color.getDarkVersion(color: Color): Color {
    return when (color) {
        TextColor -> DarkText
        Red -> DarkRed
        Blue -> DarkBlue
        else -> color
    }
}

fun Color.getLightVersion(color: Color): Color {
    return when (color) {
        DarkText -> TextColor
        DarkRed -> Red
        DarkBlue -> Blue
        else -> color
    }
}
