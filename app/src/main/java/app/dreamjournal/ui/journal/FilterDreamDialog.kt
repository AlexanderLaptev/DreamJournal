package app.dreamjournal.ui.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ListAlt
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Brightness7
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.theme.CatppuccinColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDreamDialog(
    onDismissRequest: () -> Unit,
) {
    val roundedCornersSize = 28.dp
    val dialogHeight = 228.dp
    val horizontalLineWidth = 32.dp
    val horizontalLineThickness = 4.dp

    val onDismissComponentHeight = 36.dp
    val dividerRoundedShape = 100.dp

    val mainContentStartPadding = 28.dp
    val mainContentEndPadding = 16.dp

    // TODO: replace this state by viewmodel state in the future
    val selectedButton = remember { mutableIntStateOf(1) }

    ModalBottomSheet(
        shape = RoundedCornerShape(
            topStart = roundedCornersSize,
            topEnd = roundedCornersSize
        ),
        onDismissRequest = onDismissRequest,
        containerColor = CatppuccinColors.base,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(dialogHeight),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(onDismissComponentHeight),
                contentAlignment = Alignment.Center
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .width(horizontalLineWidth)
                        .clip(shape = RoundedCornerShape(dividerRoundedShape)),
                    thickness = horizontalLineThickness,
                    color = CatppuccinColors.text,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = mainContentStartPadding,
                        end = mainContentEndPadding
                    ),
            ) {
                FilterDreamDialogComponent(
                    text = "Show all dreams",
                    icon = Icons.AutoMirrored.Rounded.ListAlt,
                    isActive = selectedButton.intValue == 1,
                    onClick = { selectedButton.intValue = 1 }
                )

                FilterDreamDialogComponent(
                    text = "Show only favorites",
                    icon = Icons.Rounded.Star,
                    iconTint = CatppuccinColors.yellow,
                    isActive = selectedButton.intValue == 2,
                    onClick = { selectedButton.intValue = 2 }
                )

                FilterDreamDialogComponent(
                    text = "Show only normal dreams",
                    icon = Icons.Rounded.DarkMode,
                    iconTint = CatppuccinColors.blue,
                    isActive = selectedButton.intValue == 3,
                    onClick = { selectedButton.intValue = 3 }
                )

                FilterDreamDialogComponent(
                    text = "Show only lucid dreams",
                    icon = Icons.Rounded.Brightness7,
                    iconTint = CatppuccinColors.mauve,
                    isActive = selectedButton.intValue == 4,
                    onClick = { selectedButton.intValue = 4 }
                )
            }
        }
    }
}

@Composable
fun FilterDreamDialogComponent(
    icon: ImageVector = Icons.Default.Air,
    iconTint: Color = CatppuccinColors.text,
    text: String = "",
    isActive: Boolean = false,
    onClick: (() -> Unit),
) {
    val rowWidth = 368.dp
    val rowHeight = 48.dp
    val insideRowWidth = 237.dp
    val insideRowHeight = 24.dp

    val textSize = 16.sp

    Row(
        modifier = Modifier
            .width(rowWidth)
            .height(rowHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .width(insideRowWidth)
                .height(insideRowHeight),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier.background(Color.Transparent),
                imageVector = icon,
                contentDescription = text,
                tint = iconTint
            )

            Text(
                text = text,
                fontSize = textSize,
                color = CatppuccinColors.text,
                textAlign = TextAlign.Left
            )
        }

        RadioButton(
            selected = isActive,
            onClick = onClick
        )
    }
}

@Preview
@Composable
fun FilterDreamDialogScreen() {
    ApplicationTheme {
        val showDialog = remember { mutableStateOf(false) }

        Scaffold(bottomBar = { TestBottomNavigationBar() }) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = CatppuccinColors.base)
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { showDialog.value = true }) {
                    Text(text = "Show dialog")
                }

                if (showDialog.value) {
                    FilterDreamDialog(onDismissRequest = { showDialog.value = false })
                }
            }
        }
    }
}

@Composable
private fun TestBottomNavigationBar() {
    BottomAppBar(
        containerColor = CatppuccinColors.base,
        contentColor = Color.White,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TestBottomNavItem(icon = Icons.Default.Home, label = "Home", onClick = {})
                TestBottomNavItem(icon = Icons.Default.Search, label = "Search", onClick = {})
                TestBottomNavItem(icon = Icons.Default.Settings, label = "Settings", onClick = {})
            }
        }
    )
}

@Composable
private fun TestBottomNavItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = Color.White)
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
    }
}
