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

        val coroutinesAndroid = kotlinX("kotlinx-coroutines-android", Versions.coroutines)
        val coroutinesCore = kotlinX("kotlinx-coroutines-core", Versions.coroutines)
        val coroutinesTest = kotlinX("kotlinx-coroutines-test", Versions.coroutines)

        private fun kotlinX(library: String, version: String): String {
            return "org.jetbrains.kotlinx:$library:$version"
        }
    }

    object Plugins {

        val gradle = gradle(Versions.plugin)

        private fun gradle(version: String) = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

}