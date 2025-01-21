package app.dreamjournal.data.dream

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import app.dreamjournal.data.ID_NOT_SET

typealias DreamId = Int

@Entity
data class Dream(
    val content: String,
    val title: String,
    val lucidity: Int,
    @PrimaryKey(autoGenerate = true) val id: DreamId = ID_NOT_SET,
) {
    @Ignore
    val isLucid: Boolean = lucidity > 0
}
