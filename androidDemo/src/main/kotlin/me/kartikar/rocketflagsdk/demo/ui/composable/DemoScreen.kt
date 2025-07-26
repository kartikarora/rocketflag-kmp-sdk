package me.kartikar.rocketflagsdk.demo.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import me.kartikar.rocketflagsdk.demo.FlagListItem
import me.kartikar.rocketflagsdk.demo.R
import me.kartikar.rocketflagsdk.demo.ui.theme.RocketFlagSdkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoScreen(flags: List<FlagListItem>) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        }
    ) { innerPadding ->
        FlagList(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            flags = flags
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DemoScreenPreview(@PreviewParameter(FlagListPreviewParameterProvider::class) flags: List<FlagListItem>) {
    RocketFlagSdkTheme {
        DemoScreen(flags = flags)
    }
}