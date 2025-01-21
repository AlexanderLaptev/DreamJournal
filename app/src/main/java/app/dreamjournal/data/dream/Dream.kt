package app.dreamjournal.data.dream

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import app.dreamjournal.data.ID_NOT_SET

typealias DreamId = Int

@Entity
data class Dream(
    val content: String,
    val epochMilli: Long,
    val title: String = "",
    val isLucid: Boolean = false,
    val lucidity: Int? = null,
    val isFavorite: Boolean = false,
    val colorIndex: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: DreamId = ID_NOT_SET,
)
