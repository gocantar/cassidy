buildscript {
    repositories {
        google()
        jcenter()
        mavenLocal()
    }
    dependencies {
        classpath(Android.Plugins.gradle)
        classpath(Kotlin.Plugins.gradle)
        classpath(Testing.Plugins.jUnit5)
        classpath(Others.Plugins.bintray)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenLocal()
    }
    apply(plugin = "test-logging")
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}