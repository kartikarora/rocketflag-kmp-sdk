# RocketFlag SDK

A Kotlin Multiplatform SDK for the [RocketFlag](https://rocketflag.app) feature flag service. The `/sdk` folder contains the SDK code that is shared across Android and Apple native targets.


## Usage

### Getting a Feature Flag

To get a feature flag, create an instance of `RocketFlag` and call the `get` method with the flag's ID. You can also provide an optional cohort or environment.

The `get` method is a `suspend` function, so it needs to be called from a coroutine or another `suspend` function.

```kotlin
// Kotlin
coroutineScope.launch {
    try {
        val flag = RocketFlag().get(flagId = "your-flag-id")
        println("Flag '${flag.name}' is ${if (flag.enabled) "enabled" else "disabled"}")
    } catch (e: Exception) {
        println("Error fetching flag: ${e.message}")
    }
}
```

```swift
// Swift
Task {
    do {
        let flag = try await RocketFlag().get(flagId: "your-flag-id", cohort: nil, environment: nil)
        print("Flag '\(flag.name)' is \(flag.enabled ? "enabled" : "disabled")")
    } catch {
        print("Error: \(error)")
    }
}
```

## Project Structure

* `/androidDemo` is an Android application that demonstrates how to use the RocketFlag SDK.
* `/iosDemo` contains an iOS application.
* `/sdk` is for the code that will be shared between all targets in the project.

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
