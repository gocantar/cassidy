object Testing {

    object Versions {
        const val jUnit5 = "5.5.2"
        const val jUnit5Plugin = "1.5.2.0"
        const val mockk = "1.9.3"
    }

    object Libraries {
        val mockK = "io.mockk:mockk:${Versions.mockk}"
        val jUnit5Api = "org.junit.jupiter:junit-jupiter-api:${Versions.jUnit5}"
        val jUnit5Engine = "org.junit.jupiter:junit-jupiter-engine:${Versions.jUnit5}"
        val jUnit5Params = "org.junit.jupiter:junit-jupiter-params:${Versions.jUnit5}"
    }

    object Plugins {
        const val jUnit5 = "de.mannodermaus.gradle.plugins:android-junit5:${Versions.jUnit5Plugin}"
    }
}