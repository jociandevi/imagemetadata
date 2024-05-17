// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "Imagemetadata",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "Imagemetadata",
            targets: ["ImageMetadataPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "ImageMetadataPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/ImageMetadataPlugin"),
        .testTarget(
            name: "ImageMetadataPluginTests",
            dependencies: ["ImageMetadataPlugin"],
            path: "ios/Tests/ImageMetadataPluginTests")
    ]
)