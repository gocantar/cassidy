[![cassidy](https://img.shields.io/badge/version-0.1.0-blue?style=flat-square)](https://github.com/gocantar/cassidy)

[![kotlin](https://img.shields.io/badge/code-Kotlin-blueviolet?style=flat-square)](https://kotlinlang.org/docs/reference/android-overview.html)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg?style=flat-square)](https://ktlint.github.io)

# CASSIDY
Cassidy is a group of libraries that help to build Android apps.

### Set Up
First of all you need to add the follow dependencies to your `build.gradle` file.
```kotlin
dependencies {
    implementation("com.gocantar.cassidy:core:$version")
    implementation("com.gocantar.cassidy:tools:$version")
    implementation("com.gocantar.cassidy:network:$version")
    implementation("com.gocantar.cassidy:widgets:$version")
}
```
To use test support library add the following dependency.
```kotlin
dependencies {
    testImplementation("com.gocantar.cassidy:test:$version")
}
```

## Core
This library give support to manage your business logic using coroutines.

[README](https://github.com/gocantar/cassidy/tree/master/core)

## Tools
This library give you tools that let write more funtional code.

[README](https://github.com/gocantar/cassidy/tree/master/tools)

## Network
[![ktlint](https://img.shields.io/badge/dependency-OkHttp3-019B85?style=flat-square)](https://github.com/square/okhttp)

This library provide you a network layer to make network calls on easy way.

[README](https://github.com/gocantar/cassidy/tree/master/network)

## Widgets
This library provide a group of widgets that that you can use to create your views.

[README](https://github.com/gocantar/cassidy/tree/master/widgets)

## Testing
This library give support to test your code when you are Cassidy libraries.

[README](https://github.com/gocantar/cassidy/tree/master/test)

