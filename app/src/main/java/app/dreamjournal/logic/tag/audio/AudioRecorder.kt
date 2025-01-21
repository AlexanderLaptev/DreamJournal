package app.dreamjournal.logic.tag.audio

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}
