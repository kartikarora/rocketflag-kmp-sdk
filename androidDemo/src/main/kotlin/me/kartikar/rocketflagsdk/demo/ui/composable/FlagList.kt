package me.kartikar.rocketflagsdk.demo.ui.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import app.rocketflag.model.Flag
import me.kartikar.rocketflagsdk.demo.FlagListItem
import me.kartikar.rocketflagsdk.demo.ui.theme.RocketFlagSdkTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FlagList(
    flags: List<FlagListItem>,
    modifier: Modifier = Modifier
) {
    val groupedFlags = flags.groupBy { it.id }

    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        groupedFlags.forEach { (_, items) ->
            stickyHeader {
                Text(
                    text = items.first().name, fontWeight = FontWeight.Bold, modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                )
            }
            items(items) { item ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    FlagItem(item = item)
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

class FlagListPreviewParameterProvider : PreviewParameterProvider<List<FlagListItem>> {
    override val values: Sequence<List<FlagListItem>> = sequenceOf(
        listOf(
            FlagListItem(
                id = "PpH5FifOCCZdbhDgGdHO",
                name = "Flag Standard",
                cohort = null,
                environment = null,
                flag = Flag(name = "flag_standard", enabled = true, id = "PpH5FifOCCZdbhDgGdHO"),
                error = null,
                loading = false
            ),
            FlagListItem(
                id = "1pFoSiRaY945TUnXG0Dk",
                name = "Flag Cohort",
                cohort = "cohort_1",
                environment = null,
                flag = null,
                error = "Invalid cohort",
                loading = false
            ),
            FlagListItem(
                id = "Z5GHBxH6ADLjFcyPTFul",
                name = "Flag Env 1",
                cohort = null,
                environment = "env_1",
                flag = null,
                error = null,
                loading = true
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun FlagListPreview(@PreviewParameter(FlagListPreviewParameterProvider::class) flags: List<FlagListItem>) {
    RocketFlagSdkTheme {
        FlagList(flags = flags)
    }
}