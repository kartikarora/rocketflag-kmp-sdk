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
            url: "https://github.com/kartikarora/rocketflag-kmp-sdk/releases/download/0.1.0/RocketFlagSdk.xcframework.zip",
            checksum: "304f3eca8d9c1b74e04c631f0df039604a2b0d0059277e2f64929585759124b5"
        )
    ]
)
