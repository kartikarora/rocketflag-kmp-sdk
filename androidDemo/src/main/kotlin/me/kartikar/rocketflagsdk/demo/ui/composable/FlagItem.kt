package me.kartikar.rocketflagsdk.demo.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import app.rocketflag.model.Flag
import me.kartikar.rocketflagsdk.demo.FlagListItem
import me.kartikar.rocketflagsdk.demo.ui.theme.RocketFlagSdkTheme

@Composable
fun FlagItem(item: FlagListItem, modifier: Modifier = Modifier) {
    OutlinedCard(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            item.cohort?.let { Text(text = "Cohort: $it") }
            item.environment?.let { Text(text = "Environment: $it") }

            if (item.loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(24.dp)
                )
            } else {
                val status = if (item.flag != null) {
                    if (item.flag.enabled) "ENABLED" else "DISABLED"
                } else {
                    item.error ?: "Unknown error"
                }
                Text(text = status)
            }
        }
    }
}

class FlagItemPreviewParameterProvider : PreviewParameterProvider<FlagListItem> {
    override val values: Sequence<FlagListItem> = sequenceOf(
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
}

@Preview(showBackground = true)
@Composable
fun FlagItemPreview(@PreviewParameter(FlagItemPreviewParameterProvider::class) item: FlagListItem) {
    RocketFlagSdkTheme {
        FlagItem(item = item)
    }
}