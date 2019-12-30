import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("android-junit5")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = Android.Versions.buildTools

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
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

    kotlinOptions.apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Android.Libraries.appCompat)
    implementation(Android.Libraries.core)
    implementation(Android.Libraries.liveData)
    implementation(kotlin(Kotlin.Modules.standard, KotlinCompilerVersion.VERSION))
    implementation("com.gocantar.cassidy:tools:0.1.0-alpha0")

    testImplementation(project(":test"))
}

apply(plugin = "publish-maven")
apply(from = "../buildSrc/src/main/kotlin/plugins/bintray-upload.gradle")