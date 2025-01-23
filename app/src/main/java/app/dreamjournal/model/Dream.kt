package app.dreamjournal.model

import app.dreamjournal.data.dream.Dream

val Dream.color get() = TagColor.entries[this.colorIndex]
