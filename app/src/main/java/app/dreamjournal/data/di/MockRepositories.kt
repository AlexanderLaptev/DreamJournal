package app.dreamjournal.data.di

import app.dreamjournal.data.dream.Dream
import app.dreamjournal.data.dream.DreamRepository
import app.dreamjournal.data.dream.Tag
import app.dreamjournal.data.dream.TagColor
import app.dreamjournal.data.dream.TagRepository
import java.time.LocalDateTime
import java.time.ZoneOffset

class MockDreamRepository : DreamRepository {
    private companion object {
        val MOCK_DREAMS = listOf(
            Dream(
                title = "Lorem ipsum",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                instant = LocalDateTime.of(2025, 1, 15, 7, 34, 13).toInstant(ZoneOffset.UTC),
                color = TagColor.Purple,
                id = 1,
            ),
            Dream(
                title = "Dolor sit",
                content = "Mauris porta, sem vel volutpat tincidunt.",
                instant = LocalDateTime.of(2025, 1, 15, 7, 28, 22).toInstant(ZoneOffset.UTC),
                color = TagColor.Green,
                id = 2,
            ),
            Dream(
                title = "Amet consectetur",
                content = "Donec id elit viverra, dictum mi a, iaculis erat.",
                instant = LocalDateTime.of(2025, 1, 15, 7, 24, 53).toInstant(ZoneOffset.UTC),
                color = TagColor.Green,
                id = 3,
            ),
        )
    }

    override suspend fun getAllDreams(): List<Dream> = MOCK_DREAMS

    override suspend fun getDreamById(id: Long): Dream = MOCK_DREAMS[id.toInt()]

    override suspend fun getDreamsByTagId(id: Long): List<Dream> = emptyList()

    override suspend fun saveDream(dream: Dream): Long = 0

    override suspend fun deleteDream(dream: Dream) = Unit

    override suspend fun deleteDreamById(id: Long) = Unit
}

class MockTagRepository : TagRepository {
    override suspend fun getAllTags(): List<Tag> = emptyList()

    override suspend fun getTagById(id: Long): Tag? = null

    override suspend fun getTagsByDreamId(id: Long): List<Tag> = emptyList()

    override suspend fun saveTag(tag: Tag): Long = 0

    override suspend fun deleteTag(tag: Tag) = Unit

    override suspend fun deleteTagById(id: Long) = Unit
}
