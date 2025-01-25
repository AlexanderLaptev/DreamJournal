package app.dreamjournal.data.di

import app.dreamjournal.data.dream.Dream
import app.dreamjournal.data.dream.DreamRepository
import app.dreamjournal.data.dream.Tag
import app.dreamjournal.data.dream.TagColor
import app.dreamjournal.data.dream.TagRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

class MockDreamRepository : DreamRepository {
    private val mockDreams = let {
        val startDate = LocalDate.of(2025, 1, 1)
        val endDate = LocalDate.of(2025, 1, 8)
        val dreamsPerDay = 3
        val startTime = LocalTime.of(7, 10)
        val increment = 10L

        val result = mutableListOf<Dream>()
        var count = 1
        for (day in startDate.datesUntil(endDate)) {
            repeat(dreamsPerDay) {
                val time = startTime.plusMinutes(it * increment)
                result += Dream(
                    title = "Lorem ipsum #$count",
                    content = """
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id elit
                        viverra, dictum mi a, iaculis erat. Duis laoreet vel libero sit amet
                        tristique. Etiam a rutrum ex, non accumsan nisl. Maecenas ac lorem
                        hendrerit, tincidunt odio nec, suscipit orci.
                    """.trimIndent().replace("\n", ""),
                    instant = LocalDateTime.of(day, time).toInstant(ZoneOffset.UTC),
                    color = TagColor.entries[count % TagColor.entries.size],
                    id = count.toLong(),
                )
                count++
            }
        }

        result
    }

    override suspend fun getAllDreams(): List<Dream> = mockDreams

    override suspend fun getDreamById(id: Long): Dream = mockDreams[id.toInt()]

    override suspend fun getDreamsByTagId(id: Long): List<Dream> = emptyList()

    override suspend fun saveDream(dream: Dream): Long = 0

    override suspend fun deleteDream(dream: Dream) = Unit

    override suspend fun deleteDreamById(id: Long) = Unit
}

class MockTagRepository : TagRepository {
    private val MOCK_TAGS = listOf(
        Tag("lorem", "😁", TagColor.White, 1),
        Tag("ipsum", "", TagColor.Red, 2),
        Tag("dolor", "💃", TagColor.Orange, 3),
        Tag("dictum", "", TagColor.Yellow, 4),
        Tag("rutrum", "🎨", TagColor.Green, 5),
        Tag("libero", "", TagColor.Teal, 6),
        Tag("tellus", "🥩", TagColor.Blue, 7),
        Tag("libero", "", TagColor.Purple, 8),
    )

    override suspend fun getAllTags(): List<Tag> = MOCK_TAGS

    override suspend fun getTagById(id: Long): Tag = MOCK_TAGS[id.toInt()]

    override suspend fun getTagsByDreamId(id: Long): List<Tag> {
        val result = mutableListOf<Tag>()
        for (i in (id + 1)..(id + 4)) {
            result += MOCK_TAGS[i.toInt() % MOCK_TAGS.size]
        }
        return result
    }

    override suspend fun saveTag(tag: Tag): Long = 0

    override suspend fun deleteTag(tag: Tag) = Unit

    override suspend fun deleteTagById(id: Long) = Unit
}
