package app.rocketflag

interface Platform {
    val platform: String
}

expect fun getPlatform(): Platform