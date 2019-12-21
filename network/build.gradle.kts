import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("junit-5")
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

    (kotlinOptions as KotlinJvmOptions).apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Cassidy
    implementation(project(":tools"))

    implementation(kotlin(Kotlin.Modules.standard, KotlinCompilerVersion.VERSION))

    implementation(group = "com.squareup", name = "okhttp-4.2.2", ext = "jar")
    implementation(group = "com.squareup", name = "okhttp-urlconnection-4.2.2", ext = "jar")

    testImplementation(project(":test"))

    testImplementation( group =  "com.squareup.okio", name = "okio", version = "2.4.2")

}
apply(from = "../buildSrc/src/main/kotlin/plugins/publish-local.gradle")