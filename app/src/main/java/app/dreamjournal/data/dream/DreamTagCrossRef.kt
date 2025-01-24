package app.dreamjournal.data.dream

import androidx.room.Entity

@Entity(primaryKeys = ["dreamId", "tagId"])
data class DreamTagCrossRef(
    val dreamId: Int,
    val tagId: Int,
)
