package app.dreamjournal.ui.journal.voice

import java.io.File

interface AudioPlayer {
    fun play(audioFile: File)
    fun stop()
}
