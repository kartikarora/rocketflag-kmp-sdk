package me.kartikar.rocketflagsdk.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.kartikar.rocketflagsdk.demo.ui.composable.DemoScreen
import me.kartikar.rocketflagsdk.demo.ui.theme.RocketFlagSdkTheme

class DemoActivity : ComponentActivity() {
    private val viewModel: RocketFlagViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RocketFlagSdkTheme {
                val flags by viewModel.flags.collectAsStateWithLifecycle()
                DemoScreen(flags = flags)
            }
        }
    }
}