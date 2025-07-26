package me.kartikar.rocketflagsdk.demo

import app.rocketflag.model.Flag

data class FlagListItem(
    val id: String,
    val name: String,
    val cohort: String?,
    val environment: String?,
    val flag: Flag?,
    val error: String?,
    val loading: Boolean
)