package app.dreamjournal.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import app.dreamjournal.ui.journal.voice.AudioPlayer
import app.dreamjournal.ui.journal.voice.AudioRecorder
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.theme.CatppuccinColors
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
    val currentPlayingVoiceMessage = remember { mutableStateOf<File?>(null) }

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
            items(voiceMessages) { voice ->
                VoiceMessageItem(
                    voice,
                    isPlaying = currentPlayingVoiceMessage.value == voice,
                    onPlayStop = {
                        if (currentPlayingVoiceMessage.value == voice) {
                            audioPlayer.stop()
                            currentPlayingVoiceMessage.value = null
                        } else {
                            audioPlayer.stop()
                            audioPlayer.play(voice)
                            currentPlayingVoiceMessage.value = voice
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
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = CatppuccinColors.mantle)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TODO: replace the text with WaveForm in the future
        Text(
            modifier = Modifier.weight(1f),
            text = file.name,
            color = CatppuccinColors.text,
        )

        Button(onClick = onPlayStop) {
            Text(text = if (isPlaying) "Stop" else "Play")
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
