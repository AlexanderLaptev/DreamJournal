package app.dreamjournal.domain

import app.dreamjournal.data.dream.DreamRepository
import app.dreamjournal.data.dream.DreamWithTags
import app.dreamjournal.data.dream.TagRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetDreamWithTagsUseCase(
    private val dreamRepository: DreamRepository,
    private val tagRepository: TagRepository,
) {
    suspend operator fun invoke(dreamId: Long): DreamWithTags {
        return withContext(Dispatchers.IO) {
            val dream = async { dreamRepository.getDreamById(dreamId) }
            val tags = async { tagRepository.getTagsByDreamId(dreamId) }
            DreamWithTags(dream.await()!!, tags.await())
        }
    }
}
