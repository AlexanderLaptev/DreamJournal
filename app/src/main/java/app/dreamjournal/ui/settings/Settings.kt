package app.dreamjournal.ui.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import app.dreamjournal.ui.theme.ThemePreference
import kotlinx.coroutines.flow.first

object Settings {
    private val KEY_THEME = stringPreferencesKey(name = "theme")

    private val Context.settingsDataStore by preferencesDataStore(name = "settings")

    suspend fun saveThemePreference(context: Context, themePreference: ThemePreference) {
        context.settingsDataStore.edit {
            it[KEY_THEME] = themePreference.name
        }
    }

    suspend fun loadThemePreference(context: Context): ThemePreference? {
        val preferences = context.settingsDataStore.data.first()
        val themePreference = preferences[KEY_THEME]
        return if (themePreference != null) ThemePreference.valueOf(themePreference) else null
    }
}
