package app.dreamjournal.ui.journal.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.dreamjournal.data.dream.DreamWithTags
import app.dreamjournal.domain.GetDreamWithTagsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DreamDetailsViewModel(
    private val getDreamWithTagsUseCase: GetDreamWithTagsUseCase,
) : ViewModel() {
    private val _dream = MutableStateFlow<DreamWithTags?>(null)
    val dream = _dream.asStateFlow()

    fun loadDream(dreamId: Long) {
        viewModelScope.launch {
            _dream.update { getDreamWithTagsUseCase(dreamId) }
        }
    }
}
