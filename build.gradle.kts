buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Android.Plugins.gradle)
        classpath(Kotlin.Plugins.gradle)
        classpath(Testing.Plugins.jUnit5)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        flatDir { dir("src/main/libs") }
    }
    apply(plugin = "test-logging")
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}