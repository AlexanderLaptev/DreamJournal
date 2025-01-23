package app.dreamjournal.model

import app.dreamjournal.data.dream.Tag

enum class TagColor {
    White,
    Red,
    Orange,
    Yellow,
    Green,
    Teal,
    Blue,
    Purple
}

val Tag.color get() = TagColor.entries[this.colorIndex]
