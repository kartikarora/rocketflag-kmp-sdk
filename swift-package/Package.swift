// swift-tools-version:5.7

import PackageDescription

let package = Package(
    name: "RocketFlagSdk",
    platforms: [
        .iOS(.v14)
    ],
    products: [
        .library(
            name: "RocketFlagSdk",
            targets: ["RocketFlagSdk"]
        )
    ],
    targets: [
        .binaryTarget(
            name: "RocketFlagSdk",
            url: "https://github.com/kartikarora/rocketflag-kmp-sdk/releases/download/{{SDK_VERSION}}/RocketFlagSdk.xcframework.zip",
            checksum: "{{SDK_CHECKSUM}}"
        )
    ]
)
