import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id("com.android.library")
    id("de.mannodermaus.android-junit5")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = Versions.buildTools

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")
    sourceSets["test"].java.srcDir("src/test/kotlin")

    (kotlinOptions as KotlinJvmOptions).apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Cassidy
    implementation(project(":tools"))

    // Kotlin
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))

    // Android
    implementation("androidx.core:core-ktx:${Versions.androidX}")

    // Unit test
    testImplementation(project(":test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.jUnit5}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.jUnit5}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${Versions.jUnit5}")
}
apply(from = "../buildSrc/publish-local.gradle")