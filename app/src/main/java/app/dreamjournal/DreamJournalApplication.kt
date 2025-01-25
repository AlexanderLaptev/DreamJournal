package app.dreamjournal

import android.app.Application
import app.dreamjournal.data.di.databaseModule
import app.dreamjournal.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DreamJournalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@DreamJournalApplication)
            appModules()
        }
    }
}

internal fun KoinApplication.appModules() {
    modules(databaseModule, uiModule)
}
