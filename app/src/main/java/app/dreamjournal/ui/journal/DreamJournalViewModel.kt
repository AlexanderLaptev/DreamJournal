package app.dreamjournal.ui.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.dreamjournal.data.dream.DreamRepository
import app.dreamjournal.data.dream.DreamWithTags
import app.dreamjournal.data.dream.TagRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

class DreamJournalViewModel(
    private val dreamRepository: DreamRepository,
    private val tagRepository: TagRepository,
) : ViewModel() {
    private val _dreamGroups = MutableStateFlow(emptyList<DreamGroupState>())
    val dreamGroups = _dreamGroups.asStateFlow()

    init {
        reloadDreams()
    }

    private fun reloadDreams() {
        viewModelScope.launch {
            val dreams = async(Dispatchers.IO) {
                dreamRepository.getAllDreams()
            }.await()

            val result = async {
                dreams
                    .groupBy { dream ->
                        LocalDateTime.ofInstant(
                            dream.instant,
                            ZoneOffset.UTC
                        ).toLocalDate()
                    }
                    .map { entry ->
                        val withTags = entry.value.map {
                            val tags = async(Dispatchers.IO) {
                                tagRepository.getTagsByDreamId(it.id)
                            }.await()
                            DreamWithTags(it, tags)
                        }.toMutableList()
                        withTags.sortByDescending { it.dream.instant }
                        DreamGroupState(entry.key, withTags)
                    }
                    .toMutableList()
            }.await()
            result.sortByDescending { it.date }
            _dreamGroups.update { result }
        }
    }
}
