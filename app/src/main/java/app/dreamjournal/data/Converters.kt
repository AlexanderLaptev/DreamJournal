package app.dreamjournal.data

import androidx.room.TypeConverter
import app.dreamjournal.data.dream.TagColor
import java.time.Instant

class Converters {
    @TypeConverter
    fun instantToLong(instant: Instant): Long = instant.epochSecond

    @TypeConverter
    fun longToInstant(value: Long): Instant = Instant.ofEpochSecond(value)

    @TypeConverter
    fun tagColorToIndex(tagColor: TagColor): Int = tagColor.ordinal

    @TypeConverter
    fun indexToTagColor(index: Int): TagColor = TagColor.entries[index]
}
