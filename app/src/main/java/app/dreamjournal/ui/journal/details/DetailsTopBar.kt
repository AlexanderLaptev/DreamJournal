package app.dreamjournal.ui.journal.details

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.dreamjournal.R
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.theme.FavoriteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    isFavorite: Boolean = false,
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {},
    onFavorite: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.screen_details),
            )
        },

        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.button_back_desc)
                )
            }
        },

        actions = {
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = stringResource(R.string.button_edit_desc)
                )
            }

            IconButton(onClick = onFavorite) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = stringResource(R.string.button_favorite_desc),
                    tint = if (isFavorite) FavoriteColor else LocalContentColor.current,
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = stringResource(R.string.button_delete_desc)
                )
            }
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    ApplicationTheme {
        DetailsTopBar(
            isFavorite = true,
        )
    }
}
