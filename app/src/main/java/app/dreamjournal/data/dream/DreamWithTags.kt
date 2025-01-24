package app.dreamjournal.data.dream

import androidx.compose.runtime.Immutable

@Immutable
data class DreamWithTags(
    val dream: Dream,
    val tags: List<Tag>,
)
