import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = Android.Versions.buildTools

    androidExtensions.isExperimental = true

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")
    sourceSets["test"].java.srcDir("src/test/kotlin")
    sourceSets["androidTest"].java.srcDir("src/androidTest/kotlin")

    kotlinOptions.apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Android.Libraries.appCompat)
    implementation(Android.Libraries.constraintLayout)
    implementation(Android.Libraries.core)
    implementation(Android.Libraries.lifecycle)
    implementation(Android.Libraries.liveData)
    implementation(Android.Libraries.viewModel)
    implementation(kotlin(Kotlin.Modules.standard, KotlinCompilerVersion.VERSION))
    implementation(Kotlin.Libraries.coroutinesAndroid)
    implementation(Kotlin.Libraries.coroutinesCore)
    implementation("com.google.android.material:material:1.2.0-alpha03")

    implementation(project(":core"))

    testImplementation(kotlin(Kotlin.Modules.test))

    androidTestImplementation(Android.Libraries.espressoCore)
    androidTestImplementation(Android.Libraries.testRunner)
}
