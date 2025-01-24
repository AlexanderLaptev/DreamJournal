package app.dreamjournal.ui.journal.voice

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
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.theme.CatppuccinColors
import com.linc.audiowaveform.AudioWaveform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import linc.com.amplituda.Amplituda
import java.io.File

@Composable
fun WaveformScreenTest() {
    val context = LocalContext.current
    val audioRecorder = remember { AudioRecorder(context) }
    val externalStorageDir = context.getExternalFilesDir(null)
    val voiceMessages = remember { mutableStateListOf<File>() }
    val isRecording = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            loadVoiceMessages(
                externalStorageDir = externalStorageDir,
                voiceMessages = voiceMessages
            )
            Log.d("VoiceMessages", "Voice messages loaded successfully")
        } catch (e: Exception) {
            Log.e("VoiceMessages", "Error loading voice messages: ${e.message}")
        }
    }

    voiceMessages.forEach { println(it) }

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
            items(voiceMessages.drop(2).take(1)) { voice ->
                VoiceMessageItem(file = voice)
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp),
            onClick = {
                if (isRecording.value) {
                    stopRecording(audioRecorder, isRecording)
                    loadVoiceMessages(
                        externalStorageDir = externalStorageDir,
                        voiceMessages = voiceMessages
                    )
                } else {
                    startRecording(audioRecorder, isRecording, externalStorageDir)
                }
            }
        ) {
            Text(text = if (isRecording.value) "Stop record" else "Start record")
        }
    }
}

@Composable
fun VoiceMessageItem(file: File) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    val waveformData = remember { mutableStateOf<List<Int>?>(null) }
    val processedFiles = remember { mutableSetOf<String>() }

    LaunchedEffect(file) {
        if (processedFiles.contains(file.path)) {
            return@LaunchedEffect
        }

        processedFiles.add(file.path)
        isLoading.value = true
        try {
            Log.d("Waveform", "Processing audio: ${file.path}")
            val amplitudes = withContext(Dispatchers.IO) {
                Log.d("Waveform", "Processing ${file.path} in IO context")
                val result = Amplituda(context).processAudio(file.path).get()
                Log.d("Waveform", "Result: $result")
                result.amplitudesAsList()
            }

            if (amplitudes.isEmpty()) {
                Log.d("Waveform", "No amplitudes found for file: ${file.name}")
            } else {
                Log.d("Waveform", "Successfully loaded waveform data for ${file.name} with ${amplitudes.size} amplitudes")
            }

            waveformData.value = amplitudes
        } catch (e: Exception) {
            Log.d("Waveform", "Error processing audio file ${file.name}: ${e.message}", e)
            waveformData.value = emptyList()
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
            waveformData.value?.let { amplitudes ->
                if (amplitudes.isEmpty()) {
                    Text(
                        text = "No waveform data",
                        color = CatppuccinColors.text
                    )
                } else {
                    AudioWaveform(
                        modifier = Modifier
                            .width(150.dp)
                            .height(48.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        amplitudes = amplitudes,
                        waveformBrush = SolidColor(CatppuccinColors.overlay0),
                        onProgressChange = {}
                    )
                }
            } ?: Text(
                text = "No waveform data",
                color = CatppuccinColors.text
            )
        }
        Button(onClick = {}) {
            Text(text = "Play")
        }
    }
}

private fun loadVoiceMessages(
    externalStorageDir: File?,
    voiceMessages: SnapshotStateList<File>,
) {
    val voiceDir = File(externalStorageDir, "voices")
    if (voiceDir.exists()) {
        Log.d("VoiceMessages", "Found voice directory, loading files...")
        val files = voiceDir.listFiles()?.sortedBy { it.lastModified() } ?: emptyList()
        voiceMessages.clear()
        voiceMessages.addAll(files)
        Log.d("VoiceMessages", "Loaded ${files.size} files.")
    } else {
        Log.e("VoiceMessages", "Voice directory does not exist.")
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

@Preview
@Composable
fun WaveformScreenTestPreview() {
    ApplicationTheme {
        WaveformScreenTest()
    }
}
