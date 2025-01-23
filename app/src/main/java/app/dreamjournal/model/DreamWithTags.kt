package app.dreamjournal.model

import androidx.compose.runtime.Immutable
import app.dreamjournal.data.dream.Dream
import app.dreamjournal.data.dream.Tag

@Immutable
data class DreamWithTags(
    val dream: Dream,
    val tags: List<Tag>,
)
