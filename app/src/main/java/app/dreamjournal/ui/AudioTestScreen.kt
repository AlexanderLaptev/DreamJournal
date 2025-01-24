package app.dreamjournal.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import app.dreamjournal.ui.journal.voice.AudioPlayer
import app.dreamjournal.ui.journal.voice.AudioRecorder
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.theme.CatppuccinColors
import com.linc.audiowaveform.AudioWaveform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import linc.com.amplituda.Amplituda
import java.io.File

@SuppressLint("ContextCastToActivity")
@Composable
fun AudioTestScreen() {
    val context = LocalContext.current
    val audioRecorder = remember { AudioRecorder(context) }
    val audioPlayer = remember { AudioPlayer(context) }

    val externalStorageDir = context.getExternalFilesDir(null)
    val voiceMessages = remember { mutableStateListOf<File>() }
    val isRecording = remember { mutableStateOf(false) }
    val currentPlayingVM = remember { mutableStateOf<File?>(null) }
    val playingProgress = remember { mutableFloatStateOf(0f) }

    val requiredPermission = Manifest.permission.RECORD_AUDIO

    val permissionGranted = remember {
        mutableStateOf(
            checkSelfPermission(context, requiredPermission) ==
                    PackageManager.PERMISSION_GRANTED
        )
    }

    LaunchedEffect(Unit) {
        if (!permissionGranted.value) {
            ActivityCompat.requestPermissions(
                context as Activity,
                listOf(requiredPermission).toTypedArray(),
                1
            )
        }
        loadVoiceMessages(
            externalStorageDir = externalStorageDir,
            voiceMessages = voiceMessages
        )
    }

    LaunchedEffect(currentPlayingVM) {
        if (currentPlayingVM.value != null) {
            while (audioPlayer.isPlaying()) {
                playingProgress.floatValue = audioPlayer.getProgress()
                delay(50)
            }
        } else {
            playingProgress.floatValue = 0F
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CatppuccinColors.base)
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(voiceMessages.take(1)) { voice ->
                VoiceMessageItem(
                    file = voice,
                    isPlaying = currentPlayingVM.value == voice,
                    currentProgress = if (currentPlayingVM.value == voice) playingProgress.floatValue else 0F,
                    onProgressChange = { newProgress ->
                        audioPlayer.seekProgress(newProgress)
                    },
                    onPlayStop = {
                        if (currentPlayingVM.value == voice) {
                            audioPlayer.stop()
                            currentPlayingVM.value = null
                        } else {
                            audioPlayer.stop()
                            audioPlayer.play(voice)
                            currentPlayingVM.value = voice
                        }
                    }
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp),
            onClick = {
                if (isRecording.value) {
                    stopRecording(
                        recorder = audioRecorder,
                        isRecording = isRecording
                    )
                    loadVoiceMessages(
                        externalStorageDir = externalStorageDir,
                        voiceMessages = voiceMessages
                    )
                } else {
                    startRecording(
                        recorder = audioRecorder,
                        isRecording = isRecording,
                        externalStorageDir = externalStorageDir
                    )
                }
            }
        ) {
            Text(text = if (isRecording.value) "Stop record" else "Start record")
        }
    }
}

@Composable
fun VoiceMessageItem(
    file: File,
    isPlaying: Boolean,
    onPlayStop: () -> Unit,
    currentProgress: Float,
    onProgressChange: (Float) -> Unit
) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    val waveformData = remember { mutableStateOf<List<Int>?>(null) }

    LaunchedEffect(file) {
        isLoading.value = true
        try {
            Log.d("Waveform", "Processing audio: ${file.path}")
            val amplitudes = withContext(Dispatchers.IO) {
                Amplituda(context).processAudio(file.path).get().amplitudesAsList()
            }

            Log.d("Waveform", "Successfully loaded waveform data for ${file.name}")
        } catch (e: Exception) {
            Log.e("Waveform", "Error processing audio file: ${e.message}")
        } finally {
            isLoading.value = false
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = CatppuccinColors.mantle)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = CatppuccinColors.text
            )
        } else {
            waveformData.value?.takeIf { it.isNotEmpty() }?.let { amplitudes ->
                AudioWaveform(
                    modifier = Modifier
                        .width(150.dp)
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    progress = currentProgress,
                    onProgressChange = { newProgress ->
                        onProgressChange(newProgress)
                    },
                    amplitudes = amplitudes,
                    waveformBrush = SolidColor(CatppuccinColors.overlay0),
                    progressBrush = SolidColor(CatppuccinColors.mauve),
                )
            } ?: Text(
                modifier = Modifier.weight(1f),
                text = "No waveform data",
                color = CatppuccinColors.text
            )
        }
    }
}

private fun startRecording(
    recorder: AudioRecorder,
    isRecording: MutableState<Boolean>,
    externalStorageDir: File?,
) {
    val voiceDir = File(externalStorageDir, "voices").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val fileExtension = if (Build.VERSION.SDK_INT >= 29) "ogg" else "m4a"
    val fileName = "voice_${System.currentTimeMillis()}.$fileExtension"
    val outputFile = File(voiceDir, fileName).apply { createNewFile() }

    recorder.start(outputFile = outputFile)
    isRecording.value = true
}

private fun stopRecording(
    recorder: AudioRecorder,
    isRecording: MutableState<Boolean>,
) {
    recorder.stop()
    isRecording.value = false
}

private fun loadVoiceMessages(
    externalStorageDir: File?,
    voiceMessages: SnapshotStateList<File>,
) {
    val voiceDir = File(externalStorageDir, "voices")
    if (voiceDir.exists()) {
        val files = voiceDir.listFiles()?.sortedBy { it.lastModified() } ?: emptyList()
        voiceMessages.clear()
        voiceMessages.addAll(files)
    }
}

@Preview
@Composable
fun AudioTestScreenPreview() {
    ApplicationTheme {
        AudioTestScreen()
    }
}
