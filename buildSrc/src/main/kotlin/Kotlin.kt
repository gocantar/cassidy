object Kotlin {

    object Versions {
        const val coroutines = "1.3.2"
        const val plugin = "1.3.60"
    }

    object Modules {
        const val standard = "stdlib-jdk7"
        const val test = "test"
    }

    object Libraries {
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object Plugins {
        const val gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.plugin}"
    }
}