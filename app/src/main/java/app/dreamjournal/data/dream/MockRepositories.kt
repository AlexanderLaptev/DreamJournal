package app.dreamjournal.data.dream

import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.random.Random

object MockDreamRepository : DreamRepository {
    private val random = Random(5765)

    val DREAM_CONTENT =
        """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id elit
            viverra, dictum mi a, iaculis erat. Duis laoreet vel libero sit amet
            tristique. Etiam a rutrum ex, non accumsan nisl. Maecenas ac lorem
            hendrerit, tincidunt odio nec, suscipit orci.
        """.trimIndent().replace("\n", " ")

    val MOCK_DREAMS = let {
        val result = mutableListOf<Dream>()
        repeat(random.nextInt(2, 5)) {
            result += getRandomDream(random)
        }
        result
    }

    fun getRandomDream(random: Random = Random): Dream {
        val year = random.nextInt(2020, 2025)
        val month = random.nextInt(1, 13)
        val day = random.nextInt(1, 29)
        val hour = random.nextInt(6, 12)
        val minute = random.nextInt(20, 51)
        val second = random.nextInt(0, 60)
        val title = if (random.nextBoolean()) {
            "Lorem ipsum #${random.nextInt(1, 1000)}"
        } else ""

        return Dream(
            title = title,
            content = """
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id elit
                        viverra, dictum mi a, iaculis erat. Duis laoreet vel libero sit amet
                        tristique. Etiam a rutrum ex, non accumsan nisl. Maecenas ac lorem
                        hendrerit, tincidunt odio nec, suscipit orci.
                    """.trimIndent().replace("\n", ""),
            created = LocalDateTime.of(year, month, day, hour, minute, second)
                .toInstant(ZoneOffset.UTC),
            bedtime = null,
            wakeUpTime = null,
            isLucid = random.nextBoolean(),
            isFavorite = random.nextBoolean(),
            lucidity = random.nextInt(0, 6),
            vividness = random.nextInt(0, 6),
            clarity = random.nextInt(0, 6),
            realism = random.nextInt(0, 6),
            color = TagColor.entries.random(random),
            id = random.nextLong(),
        )
    }

    override suspend fun getAllDreams(): List<Dream> {
        delay(2000) // Simulate IO
        return MOCK_DREAMS
    }

    override suspend fun getDreamById(id: Long): Dream = MOCK_DREAMS[id.toInt()]

    override suspend fun getDreamsByTagId(id: Long): List<Dream> = emptyList()

    override suspend fun saveDream(dream: Dream): Long = 0

    override suspend fun deleteDream(dream: Dream) = Unit

    override suspend fun deleteDreamById(id: Long) = Unit
}

object MockTagRepository : TagRepository {
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
