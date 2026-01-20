import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.vanniktech.maven.publish)
    signing
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        publishLibraryVariants("release")
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    val xcf = XCFramework("RocketFlagSdk")
    listOf(
        iosX64(), iosArm64(), iosSimulatorArm64()
    ).forEach { target ->
        target.binaries.framework {
            baseName = "RocketFlagSdk"
            binaryOption("bundleId", "me.kartikarora.rocketflag.apple")
            isStatic = true

            xcf.add(this)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.bundles.ktor)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "me.kartikarora.rocketflag.android"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

mavenPublishing {
    coordinates(
        "me.kartikarora.rocketflag",
        "android",
        libs.versions.rocketflagSdk.get()
    )

    pom {
        name.set("RocketFlag SDK for Kotlin Multiplatform")
        description.set("Official Kotlin SDK for RocketFlag feature flagging service")
        url.set("https://rocketflag.app")

        licenses {
            license {
                name.set("The Apache Software License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }

        scm {
            connection.set("scm:git:github.com/kartikarora/rocketflag-kmp-sdk.git")
            developerConnection.set("scm:git:ssh://github.com/kartikarora/rocketflag-kmp-sdk.git")
            url.set("https:/github.com/kartikarora/rocketflag-kmp-sdk")
        }

        developers {
            developer {
                id.set("kartikarora")
                name.set("Kartik Arora")
                url.set("https://kartikarora.me")
            }
        }
    }
}