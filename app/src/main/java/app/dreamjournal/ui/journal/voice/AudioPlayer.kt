package app.dreamjournal.ui.journal.voice

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AudioPlayer(private val context: Context) {
    private var audioPlayer: MediaPlayer? = null

    fun play(audioFile: File) {
        MediaPlayer.create(context, audioFile.toUri()).apply {
            audioPlayer = this
            start()
        }
    }

    fun stop() {
        audioPlayer?.stop()
        audioPlayer?.release()
        audioPlayer = null
    }
}
