object Android {

    object Versions {

        const val androidX = "1.1.0"
        const val buildTools = "29.0.2"
        const val espressoCore = "3.2.0"
        const val gradleTools = "3.5.3"
        const val testRunner = "1.2.0"
    }

    object Libraries {

        val appCompat = androidX("appcompat:appcompat", Versions.androidX)
        val core = androidX("core:core-ktx", Versions.androidX)
        val espressoCore = androidX("test.espresso:espresso-core", Versions.espressoCore)
        val testRunner = androidX("test:runner", Versions.testRunner)

        private fun androidX(library: String, version: String): String {
            return "androidx.$library:$version"
        }
    }

    object Plugins {

        val gradle = toolsGradle(Versions.gradleTools)

        private fun toolsGradle(version: String) = "com.android.tools.build:gradle:$version"
    }

}