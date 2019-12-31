import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    id("de.mannodermaus.android-junit5")
    kotlin("android")
    kotlin("android.extensions")
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

    api(kotlin(Kotlin.Modules.test))
    api(Kotlin.Libraries.coroutinesTest)
    api(Testing.Libraries.mockK)

    implementation(Android.Libraries.coreTesting)
    implementation(kotlin(Kotlin.Modules.standard, KotlinCompilerVersion.VERSION))
    implementation(Testing.Libraries.jUnit5Api)
    implementation(Testing.Libraries.jUnit5Params)

    runtimeOnly(Testing.Libraries.jUnit5Engine)
}

apply(plugin = "publish-maven")
apply(from = "../buildSrc/src/main/kotlin/plugins/bintray-upload.gradle")