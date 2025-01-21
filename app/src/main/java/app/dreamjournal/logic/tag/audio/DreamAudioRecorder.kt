package app.dreamjournal.logic.tag.audio

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File
import java.io.FileOutputStream

class DreamAudioRecorder(private val context: Context) : AudioRecorder {

    private var audioRecorder: MediaRecorder? = null

    override fun start(outputFile: File) {
        createMediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            setOutputFile(FileOutputStream(outputFile).fd)

            prepare()
            start()

            audioRecorder = this
        }
    }

    override fun stop() {
        audioRecorder?.stop()
        audioRecorder?.reset()
        audioRecorder = null
    }

    private fun createMediaRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= 31) { MediaRecorder(context) }
               else { MediaRecorder() }
    }
}