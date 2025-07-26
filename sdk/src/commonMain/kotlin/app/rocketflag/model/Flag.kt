package app.rocketflag.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Flag(
    val name: String,
    val enabled: Boolean,
    val id: String,
)