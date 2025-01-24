package app.dreamjournal.data.dream

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface DreamRepository {
    @Query("SELECT * FROM Dream")
    suspend fun getAllDreams(): List<Dream>

    @Query("SELECT * FROM Dream WHERE id = :id")
    suspend fun getDreamById(id: Int): Dream?

    @Query(
        """
            SELECT * FROM Dream
                INNER JOIN DreamTagCrossRef AS DT ON Dream.id = DT.dreamId
                WHERE DT.tagId = :id
        """
    )
    suspend fun getDreamsByTagId(id: Int): List<Dream>

    @Upsert
    suspend fun saveDream(dream: Dream): Int

    @Delete
    suspend fun deleteDream(dream: Dream)

    @Query("DELETE FROM Dream WHERE id = :id")
    suspend fun deleteDreamById(id: Int)
}
