package app.dreamjournal.data.dream

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TagRepository {
    @Query("SELECT * FROM Tag")
    suspend fun getAllTags(): List<Tag>

    @Query("SELECT * FROM Tag WHERE id = :id")
    suspend fun getTagById(id: Long): Tag?

    @Query(
        """
            SELECT * FROM Tag
                INNER JOIN DreamTagCrossRef AS DT ON Tag.id = DT.tagId
                WHERE DT.dreamId = :id
        """
    )
    suspend fun getTagsByDreamId(id: Long): List<Tag>

    @Upsert
    suspend fun saveTag(tag: Tag): Long

    @Delete
    suspend fun deleteTag(tag: Tag)

    @Query("DELETE FROM Tag WHERE id = :id")
    suspend fun deleteTagById(id: Long)
}
