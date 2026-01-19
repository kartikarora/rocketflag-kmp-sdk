# RocketFlag SDK

[![GitHub release (latest by date)](https://img.shields.io/github/v/release/kartikarora/rocketflag-kmp-sdk)](https://github.com/kartikarora/rocketflag-kmp-sdk/releases)
[![Maven Central](https://img.shields.io/maven-central/v/me.kartikarora.rocketflag/android)](https://central.sonatype.com/artifact/me.kartikarora.rocketflag/android)
[![Build & Verify (Main)](https://github.com/kartikarora/rocketflag-kmp-sdk/actions/workflows/build-main.yml/badge.svg)](https://github.com/kartikarora/rocketflag-kmp-sdk/actions/workflows/build-main.yml)
[![Build & Verify (PR)](https://github.com/kartikarora/rocketflag-kmp-sdk/actions/workflows/build-pr.yml/badge.svg)](https://github.com/kartikarora/rocketflag-kmp-sdk/actions/workflows/build-pr.yml)
[![Release Pipeline](https://github.com/kartikarora/rocketflag-kmp-sdk/actions/workflows/release-pipeline.yml/badge.svg)](https://github.com/kartikarora/rocketflag-kmp-sdk/actions/workflows/release-pipeline.yml)

A Kotlin Multiplatform SDK for the [RocketFlag](https://rocketflag.app) feature flag service. The `/sdk` folder contains the SDK code that is shared across Android and Apple native targets.

## Features

- **Multiplatform Support**: Use the same SDK in your Android and iOS projects.
- **Easy Integration**: Simple API to fetch feature flags.
- **Support for Cohorts and Environments**: Evaluate flags based on user cohorts or different environments.
- **Lightweight**: Minimal dependencies, built on Ktor and Kotlinx Serialization.

## Documentation

For more detailed information, visit the official [RocketFlag Documentation](https://rocketflag.app/docs/).

## Installation

### Android

Add the dependency to your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("me.kartikarora.rocketflag:android:<latest-version>")
}
```

### iOS (Swift Package Manager)

Add the following package to your `Package.swift` or via Xcode:

```swift
dependencies: [
    .package(url: "https://github.com/kartikarora/rocketflag-kmp-sdk.git", from: "<latest-version>")
]
```

## Usage

### Initialization

Create an instance of `RocketFlag`:

```kotlin
val rocketFlag = RocketFlag()
```

### Fetching a Feature Flag

To get a feature flag, call the `get` method with the flag's ID. You can also provide an optional cohort or environment.

The `get` method is a `suspend` function, so it needs to be called from a coroutine or another `suspend` function.

#### Kotlin (Android/KMP)

```kotlin
import app.rocketflag.RocketFlag
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

val scope = MainScope()

scope.launch {
    try {
        val flag = RocketFlag().get(
            flagId = "your-flag-id",
            cohort = "beta-users",      // Optional
            environment = "production"  // Optional
        )
        println("Flag '${flag.name}' is ${if (flag.enabled) "enabled" else "disabled"}")
    } catch (e: Exception) {
        println("Error fetching flag: ${e.message}")
    }
}
```

#### Swift (iOS)

```swift
import RocketFlagSdk

Task {
    do {
        let flag = try await RocketFlag().get(
            flagId: "your-flag-id",
            cohort: "beta-users",      // Optional
            environment: "production"  // Optional
        )
        print("Flag '\(flag.name)' is \(flag.enabled ? "enabled" : "disabled")")
    } catch {
        print("Error: \(error)")
    }
}
```

## Project Structure

* `/androidDemo`: An Android application demonstrating how to use the RocketFlag SDK.
* `/iosDemo`: An iOS application demonstrating how to use the RocketFlag SDK.
* `/sdk`: The Kotlin Multiplatform SDK code shared between all targets.

---

For more information, visit [rocketflag.app](https://rocketflag.app/).
