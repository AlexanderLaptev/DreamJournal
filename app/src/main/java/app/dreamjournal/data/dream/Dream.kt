package app.dreamjournal.data.dream

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import app.dreamjournal.data.ID_NOT_SET
import java.time.Instant
import java.time.LocalTime

@Entity
@Immutable
data class Dream(
    val title: String,
    val content: String,
    val created: Instant,
    val bedtime: LocalTime?,
    val wakeUpTime: LocalTime?,
    val isLucid: Boolean,
    val isFavorite: Boolean,
    val lucidity: Int,
    val vividness: Int,
    val clarity: Int,
    val realism: Int,
    val color: TagColor,
    @PrimaryKey(autoGenerate = true) val id: Long = ID_NOT_SET,
) {
    @Ignore
    val isSaved = id != ID_NOT_SET
}
