package app.dreamjournal.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import app.dreamjournal.logic.tag.audio.DreamAudioPlayer
import app.dreamjournal.logic.tag.audio.DreamAudioRecorder
import app.dreamjournal.ui.theme.CatppuccinColors
import java.io.File

@SuppressLint("ContextCastToActivity")
@Composable
fun AudioTestScreen() {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val permission = Manifest.permission.RECORD_AUDIO
    val permissionState = remember {
        mutableStateOf(
            checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        )
    }

    LaunchedEffect(Unit) {
        if (!permissionState.value) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), 1)
        }
    }

    val audioRecorder by lazy { DreamAudioRecorder(context) }
    val audioPlayer by lazy { DreamAudioPlayer(context) }

    val isRecording = remember { mutableStateOf(false) }
    val isPlaying = remember { mutableStateOf(false) }

    var voiceMessage: File? = null

    Row(
        modifier = Modifier
            .background(color = CatppuccinColors.base)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                if (!isRecording.value) {
                    File(context.cacheDir, "voice_message.mp3").also { audio ->
                        audioRecorder.start(audio)
                        voiceMessage = audio
                    }
                } else {
                    audioRecorder.stop()
                }

                isRecording.value = !isRecording.value
            }
        ) {
            val text = if (!isRecording.value) "Start record" else "Stop record"
            Text(text = text)
        }

        Spacer(modifier = Modifier.width(32.dp))
        Button(
            onClick = {
                if (!isPlaying.value) {
                    audioPlayer.play(audioFile = voiceMessage ?: return@Button)
                } else {
                    audioPlayer.stop()
                }

                isPlaying.value = !isPlaying.value
            }
        ) {
            val text = if (!isPlaying.value) "Play" else "Stop play"
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun AudioTestScreenPreview() {
    AudioTestScreen()
}
