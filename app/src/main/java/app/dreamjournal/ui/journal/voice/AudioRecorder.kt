package app.dreamjournal.ui.journal.voice

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}
