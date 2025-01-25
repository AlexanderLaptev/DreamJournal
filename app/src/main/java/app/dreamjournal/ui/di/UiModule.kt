package app.dreamjournal.ui.di

import app.dreamjournal.ui.journal.DreamJournalViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::DreamJournalViewModel)
}
