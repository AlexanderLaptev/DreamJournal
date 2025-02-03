package app.dreamjournal.ui.journal.details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.dreamjournal.R
import app.dreamjournal.data.dream.MockTagRepository
import app.dreamjournal.data.dream.Tag
import app.dreamjournal.ui.shared.Tag
import app.dreamjournal.ui.theme.CatppuccinColors

private val OVERFLOW_TAG = Tag("...")

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagList(
    tags: List<Tag>,
) {
    var expanded by remember { mutableStateOf(false) }
    val padding = PaddingValues(
        top = 8.dp,
        start = 16.dp,
        end = 16.dp,
        bottom = 16.dp
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = CatppuccinColors.mantle,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(padding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.tag_list),
                style = MaterialTheme.typography.titleMedium,
                color = CatppuccinColors.text
            )

            IconButton(onClick = {
                expanded = !expanded
            }) {
                Icon(
                    imageVector = if (expanded) {
                        Icons.Rounded.KeyboardArrowUp
                    } else Icons.Rounded.KeyboardArrowDown,

                    contentDescription = stringResource(
                        if (expanded) {
                            R.string.tag_list_expand
                        } else R.string.tag_list_collapse
                    ),

                    tint = CatppuccinColors.text
                )
            }
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            maxLines = if (expanded) Int.MAX_VALUE else 2,
            overflow = FlowRowOverflow.expandIndicator { Tag(OVERFLOW_TAG) }
        ) {
            tags.forEach { tag -> Tag(tag = tag) }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun Preview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = CatppuccinColors.base)
            .padding(16.dp)
    ) {
        TagList(tags = MockTagRepository.MOCK_TAGS)
    }
}
