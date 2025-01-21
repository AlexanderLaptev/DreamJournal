package app.dreamjournal.data.dream

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.dreamjournal.data.ID_NOT_SET

typealias TagId = Int

@Entity
data class Tag(
    val label: String,
    val emoji: String = "",
    val colorIndex: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: TagId = ID_NOT_SET,
)
