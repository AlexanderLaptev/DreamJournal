package app.dreamjournal.ui.journal

import androidx.lifecycle.ViewModel
import app.dreamjournal.data.dream.DreamRepository
import app.dreamjournal.data.dream.TagRepository

class DreamJournalViewModel(
    val dreamRepository: DreamRepository,
    val tagRepository: TagRepository,
) : ViewModel()
