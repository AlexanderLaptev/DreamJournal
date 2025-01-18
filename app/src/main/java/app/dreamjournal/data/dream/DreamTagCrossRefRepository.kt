package app.dreamjournal.data.dream

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface DreamTagCrossRefRepository {
    @Insert
    suspend fun addCrossRef(ref: DreamTagCrossRef)

    @Delete
    suspend fun removeCrossRef(ref: DreamTagCrossRef)
}
