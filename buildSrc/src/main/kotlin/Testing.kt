object Testing {

    object Versions {

        const val jUnit5 = "5.5.2"
        const val jUnit5Plugin = "1.5.2.0"
        const val mockk = "1.9.3"
    }

    object Libraries {

        val jUnitApi = jUnit("junit-jupiter-api", Versions.jUnit5)
        val jUnitEngine = jUnit("junit-jupiter-engine", Versions.jUnit5)
        val jUnitParams = jUnit("junit-jupiter-params", Versions.jUnit5)
        val mockK = mockK(Versions.mockk)

        private fun jUnit(library: String, version: String) = "org.junit.jupiter:$library:$version"
        private fun mockK(version: String) = "io.mockk:mockk:$version"
    }

    object Plugins {

        val jUnit = jUnit(Versions.jUnit5Plugin)

        private fun jUnit(version: String) = "de.mannodermaus.gradle.plugins:android-junit5:$version"
    }
}