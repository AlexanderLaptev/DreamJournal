package app.dreamjournal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import app.dreamjournal.data.di.mockDatabaseModule
import app.dreamjournal.domain.GetDreamWithTagsUseCase
import app.dreamjournal.ui.journal.DreamJournalViewModel
import app.dreamjournal.ui.journal.details.DreamDetailsViewModel
import app.dreamjournal.ui.settings.Settings
import app.dreamjournal.ui.theme.DreamJournalTheme
import app.dreamjournal.ui.theme.LocalThemePreferenceProvider
import app.dreamjournal.ui.theme.ThemePreference
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinApplication
import org.koin.core.logger.Level
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KoinApplication(application = {
                androidLogger(Level.DEBUG)
                androidContext(this@MainActivity)
                modules(mockDatabaseModule, module {
                    singleOf(::GetDreamWithTagsUseCase)
                    viewModelOf(::DreamJournalViewModel)
                    viewModelOf(::DreamDetailsViewModel)
                })
            }) {
                var currentTheme: ThemePreference by rememberSaveable {
                    val initial = runBlocking { Settings.loadThemePreference(this@MainActivity) }
                        ?: ThemePreference.System
                    mutableStateOf(initial)
                }

                val onThemeChange: (ThemePreference) -> Unit = {
                    newTheme -> currentTheme = newTheme
                }

                LaunchedEffect(Unit) {
                    val savedTheme = Settings.loadThemePreference(this@MainActivity)
                    currentTheme = savedTheme ?: ThemePreference.System
                }

                CompositionLocalProvider(LocalThemePreferenceProvider provides currentTheme) {
                    DreamJournalTheme(currentTheme) {
                        DreamJournalNavGraph(onThemeChange = onThemeChange)
                    }
                }
            }
        }
    }
}
