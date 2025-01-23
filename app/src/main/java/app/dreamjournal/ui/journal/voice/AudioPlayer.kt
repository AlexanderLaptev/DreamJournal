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

    fun isPlaying(): Boolean {
        return audioPlayer?.isPlaying ?: false
    }

    fun getProgress(): Float {
        audioPlayer?.let {
            if (it.duration > 0) {
                return it.currentPosition / it.duration.toFloat()
            }
        }

        return 0f
    }

    fun seekProgress(progress: Float) {
        audioPlayer?.let {
            val pos = (progress * it.duration).toInt()
            it.seekTo(pos)
        }
    }
}
