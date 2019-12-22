object Android {

    object Versions {
        const val androidX = "1.1.0"
        const val buildTools = "29.0.2"
        const val espressoCore = "3.2.0"
        const val gradleTools = "3.5.3"
        const val testRunner = "1.2.0"
    }

    object Libraries {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.androidX}"
        const val core = "androidx.core:core-ktx:${Versions.androidX}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
        const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    }

    object Plugins {
        const val gradle = "com.android.tools.build:gradle:${Versions.gradleTools}"
    }
}