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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class DreamJournalViewModel(
    private val dreamRepository: DreamRepository,
    private val tagRepository: TagRepository,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<DreamJournalScreenUiState>(DreamJournalScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        reloadDreams()
    }

    private fun reloadDreams() {
        _uiState.update { DreamJournalScreenUiState.Loading }
        viewModelScope.launch(Dispatchers.Default) {
            val dreams = async(Dispatchers.IO) {
                dreamRepository.getAllDreams()
            }.await()

            val entries = async {
                dreams
                    .groupBy { dream ->
                        LocalDateTime.ofInstant(
                            dream.created,
                            ZoneOffset.UTC
                        ).toLocalDate()
                    }
                    .map { entry ->
                        val withTags = entry.value.map { dream ->
                            val tags = async(Dispatchers.IO) {
                                tagRepository.getTagsByDreamId(dream.id)
                            }.await()
                            DreamWithTags(dream, tags)
                        }.toMutableList()
                        withTags.sortByDescending { it.dream.created }
                        DreamGroupUiState(entry.key, withTags)
                    }
                    .toMutableList()
            }.await()

            if (entries.isEmpty()) {
                _uiState.update { DreamJournalScreenUiState.NoEntries }
            } else {
                entries.sortByDescending { it.date }
                _uiState.update { DreamJournalScreenUiState.Loaded(entries) }
            }
        }
    }
}

data class DreamGroupUiState(
    val date: LocalDate,
    val dreamsWithTags: List<DreamWithTags>,
)

sealed interface DreamJournalScreenUiState {
    data object Loading : DreamJournalScreenUiState
    data object NoEntries : DreamJournalScreenUiState
    data class Loaded(val groups: List<DreamGroupUiState>) : DreamJournalScreenUiState
}
