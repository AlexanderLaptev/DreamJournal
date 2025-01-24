package app.dreamjournal.data.dream

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import app.dreamjournal.data.ID_NOT_SET

@Entity
@Immutable
data class Tag(
    val label: String,
    val emoji: String = "",
    val colorIndex: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int = ID_NOT_SET,
) {
    @Ignore
    val color = TagColor.entries[colorIndex]

    @Ignore
    val isSaved = id != ID_NOT_SET
}
