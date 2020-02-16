object Android {

    object Versions {
        const val androidX = "1.1.0"
        const val archCore = "2.1.0"
        const val buildTools = "29.0.2"
        const val constraintLayout = "2.0.0-beta4"
        const val espressoCore = "3.2.0"
        const val gradleTools = "3.5.3"
        const val lifecycle = "2.2.0-rc03"
        const val testRunner = "1.2.0"
    }

    object Libraries {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.androidX}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val core = "androidx.core:core-ktx:${Versions.androidX}"
        const val coreTesting = "androidx.arch.core:core-testing:${Versions.archCore}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
        const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    }

    object Plugins {
        const val gradle = "com.android.tools.build:gradle:${Versions.gradleTools}"
    }
}