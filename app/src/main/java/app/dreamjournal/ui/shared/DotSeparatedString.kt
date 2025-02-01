package app.dreamjournal.ui.shared

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun DotSeparatedString(
    vararg strings: String,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        strings.forEachIndexed { i, it ->
            Text(
                text = it,
                style = style,
                color = color,
            )
            if (i != strings.lastIndex) {
                Canvas(Modifier.size(4.dp)) { drawCircle(color) }
            }
        }
    }
}
