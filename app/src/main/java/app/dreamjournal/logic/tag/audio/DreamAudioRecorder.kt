package app.dreamjournal.logic.tag.audio

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File

class DreamAudioRecorder(private val context: Context) : AudioRecorder {

    private var audioRecorder: MediaRecorder? = null

    override fun start(outputFile: File) {
        createMediaRecorder(outputFile = outputFile).apply {
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

    private fun createMediaRecorder(outputFile: File): MediaRecorder {
        val recorder = if (Build.VERSION.SDK_INT >= 31) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            recorder.setOutputFormat(MediaRecorder.OutputFormat.OGG)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.OPUS)
        } else {
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        }
        recorder.setOutputFile(outputFile.absolutePath)

        return recorder
    }
}
