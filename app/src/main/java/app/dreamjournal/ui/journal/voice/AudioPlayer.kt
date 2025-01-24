package app.dreamjournal.ui.journal.voice

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AudioPlayer(private val context: Context) {
    private var audioPlayer: MediaPlayer? = null
    private var isPaused = false
    private var lastPlayedFile: File? = null

    fun play(audioFile: File) {
        if (lastPlayedFile == audioFile && isPaused) {
            audioPlayer?.start()
            isPaused = false
        } else {
            stop()
            lastPlayedFile = audioFile

            audioPlayer = MediaPlayer.create(context, audioFile.toUri()).apply {
                setOnCompletionListener {
                    stop()
                }
                start()
            }
            isPaused = false
        }
    }

    fun pause() {
        audioPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                isPaused = true
            }
        }
    }

    fun stop() {
        audioPlayer?.stop()
        audioPlayer?.release()
        audioPlayer = null
        isPaused = false
        lastPlayedFile = null
    }

    fun isPlaying(): Boolean {
        return audioPlayer?.isPlaying ?: false
    }

    fun isPaused(): Boolean {
        return isPaused
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
