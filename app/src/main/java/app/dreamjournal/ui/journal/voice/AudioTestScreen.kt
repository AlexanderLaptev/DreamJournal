package app.dreamjournal.ui.journal.voice

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PauseCircle
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.StopCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.theme.CatppuccinColors
import com.linc.audiowaveform.AudioWaveform
import kotlinx.coroutines.delay
import linc.com.amplituda.Amplituda
import java.io.File

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
            checkSelfPermission(context, requiredPermission) == PackageManager.PERMISSION_GRANTED
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
        loadVoiceMessages(externalStorageDir = externalStorageDir, voiceMessages = voiceMessages)
    }

    LaunchedEffect(currentPlayingVM.value) {
        if (currentPlayingVM.value != null) {
            while (audioPlayer.isPlaying()) {
                playingProgress.floatValue = audioPlayer.getProgress()
                delay(50)
            }
            currentPlayingVM.value = null
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
            modifier = Modifier
                .weight(1f)
                .background(color = CatppuccinColors.mantle, shape = RoundedCornerShape(12.dp)),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(voiceMessages) { voice ->
                val isPlaying = currentPlayingVM.value == voice

                VoiceMessageItem(
                    file = voice,
                    audioPlayer = audioPlayer,
                    isPlaying = isPlaying,
                    currentProgress = if (currentPlayingVM.value == voice) playingProgress.floatValue else 0F,
                    onProgressChange = { newProgress ->
                        audioPlayer.seekProgress(newProgress)
                        playingProgress.floatValue = newProgress
                    },
                    onPlayStop = {
                        if (currentPlayingVM.value == voice) {
                            if (audioPlayer.isPaused()) {
                                audioPlayer.play(voice)
                            } else {
                                audioPlayer.pause()
                            }
                        } else {
                            currentPlayingVM.value = voice
                            playingProgress.floatValue = 0f
                            audioPlayer.play(voice)
                        }
                    },
                    onReset = {
                        audioPlayer.stop()
                        currentPlayingVM.value = null
                        playingProgress.floatValue = 0F
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
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
    audioPlayer: AudioPlayer,
    isPlaying: Boolean,
    onPlayStop: () -> Unit,
    onReset: () -> Unit,
    currentProgress: Float,
    onProgressChange: (Float) -> Unit
) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    val waveformData = remember { mutableStateOf<List<Int>?>(null) }

    val componentHeight = 48.dp
    val buttonSpacing = 4.dp
    val waveformToButtonsSpacing = 12.dp

    LaunchedEffect(file) {
        isLoading.value = true
        try {
            waveformData.value = Amplituda(context).processAudio(file.path).get().amplitudesAsList()
        } catch (e: Exception) {
            Log.e("Waveform", "Error processing audio file: ${e.message}")
        } finally {
            isLoading.value = false
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .height(componentHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier.size(componentHeight),
                color = CatppuccinColors.text
            )
        } else {
            waveformData.value?.let { amplitudes ->
                if (amplitudes.isEmpty()) {
                    NoWaveformDataText(modifier = Modifier.weight(1f))
                } else {

                    // This waveform doesn't display the played part in one color and the unplayed part in another color when paused
                    // TODO: replace this AudioWaveform with a custom waveform in the future
                    AudioWaveform(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = waveformToButtonsSpacing),
                        progress = currentProgress,
                        onProgressChange = onProgressChange,
                        amplitudes = amplitudes,
                        waveformBrush = if (isPlaying) {
                            SolidColor(CatppuccinColors.overlay0)
                        } else {
                            SolidColor(CatppuccinColors.mauve)
                        },
                        progressBrush = SolidColor(CatppuccinColors.mauve),
                    )

                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        IconButton(onClick = {
                            if (isPlaying) {
                                audioPlayer.pause()
                            } else {
                                onPlayStop()
                            }
                        }) {
                            Icon(
                                imageVector = if (isPlaying && !audioPlayer.isPaused()) Icons.Rounded.PauseCircle else Icons.Rounded.PlayCircle,
                                contentDescription = if (isPlaying && !audioPlayer.isPaused()) "Pause" else "Play",
                                tint = if (isPlaying) CatppuccinColors.blue else CatppuccinColors.text
                            )
                        }

                        IconButton(onClick = onReset) {
                            Icon(
                                imageVector = Icons.Rounded.StopCircle,
                                contentDescription = "Reset",
                                tint = CatppuccinColors.text
                            )
                        }
                    }
                }
            } ?: NoWaveformDataText(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun NoWaveformDataText(modifier: Modifier = Modifier) {
    Text(
        text = "No waveform data",
        color = CatppuccinColors.text,
        textAlign = TextAlign.Left,
        modifier = modifier
    )
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
