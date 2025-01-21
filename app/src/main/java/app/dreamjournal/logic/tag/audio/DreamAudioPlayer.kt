package app.dreamjournal.logic.tag.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class DreamAudioPlayer(private val context: Context) : AudioPlayer {

    private var audioPlayer: MediaPlayer? = null

    override fun play(audioFile: File) {
        MediaPlayer.create(context, audioFile.toUri()).apply {
            audioPlayer = this
            start()
        }
    }

    override fun stop() {
        audioPlayer?.stop()
        audioPlayer?.release()
        audioPlayer = null
    }
}
