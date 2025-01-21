package app.dreamjournal.logic.tag.audio

import java.io.File

interface AudioPlayer {
    fun play(audioFile: File)
    fun stop()
}
