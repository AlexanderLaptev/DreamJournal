package app.dreamjournal.ui.journal.details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.theme.CatppuccinColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    isFavorite: Boolean = false,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CatppuccinColors.base,
            titleContentColor = CatppuccinColors.text,
            navigationIconContentColor = CatppuccinColors.text,
            actionIconContentColor = CatppuccinColors.subtext0
        ),
        title = {
            Text(
                text = "Details",
                fontSize = 22.sp,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        },
        actions = {
            IconButton(onClick = { onEditClick() }) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "Edit"
                )
            }

            IconButton(onClick = { onFavoriteClick() }) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) CatppuccinColors.yellow else CatppuccinColors.subtext0
                )
            }

            IconButton(onClick = { onRemoveClick() }) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    )
}

@Preview
@Composable
fun Preview() {
    ApplicationTheme {
        DetailsTopBar(
            isFavorite = true,
            onBackClick = {},
            onEditClick = {},
            onFavoriteClick = {},
            onRemoveClick = {}
        )
    }
}
