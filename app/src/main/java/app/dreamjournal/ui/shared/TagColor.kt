package app.dreamjournal.ui.shared

import androidx.compose.ui.graphics.Color
import app.dreamjournal.ui.theme.tag_blue
import app.dreamjournal.ui.theme.tag_blue_text
import app.dreamjournal.ui.theme.tag_green
import app.dreamjournal.ui.theme.tag_green_text
import app.dreamjournal.ui.theme.tag_orange
import app.dreamjournal.ui.theme.tag_orange_text
import app.dreamjournal.ui.theme.tag_purple
import app.dreamjournal.ui.theme.tag_purple_text
import app.dreamjournal.ui.theme.tag_red
import app.dreamjournal.ui.theme.tag_red_text
import app.dreamjournal.ui.theme.tag_teal
import app.dreamjournal.ui.theme.tag_teal_text
import app.dreamjournal.ui.theme.tag_white
import app.dreamjournal.ui.theme.tag_white_text
import app.dreamjournal.ui.theme.tag_yellow
import app.dreamjournal.ui.theme.tag_yellow_text

enum class TagColor(
    val body: Color,
    val text: Color,
) {
    White(tag_white, tag_white_text),
    Red(tag_red, tag_red_text),
    Orange(tag_orange, tag_orange_text),
    Yellow(tag_yellow, tag_yellow_text),
    Green(tag_green, tag_green_text),
    Teal(tag_teal, tag_teal_text),
    Blue(tag_blue, tag_blue_text),
    Purple(tag_purple, tag_purple_text),
}
