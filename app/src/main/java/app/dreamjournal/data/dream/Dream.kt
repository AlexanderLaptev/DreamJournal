package app.dreamjournal.data.dream

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import app.dreamjournal.data.ID_NOT_SET
import java.time.Instant

@Entity
@Immutable
data class Dream(
    val content: String,
    val instant: Instant,
    val title: String = "",
    val isLucid: Boolean = false,
    val isFavorite: Boolean = false,
    val color: TagColor = TagColor.White,
    @PrimaryKey(autoGenerate = true) val id: Long = ID_NOT_SET,
) {
    @Ignore
    val isSaved = id != ID_NOT_SET
}
