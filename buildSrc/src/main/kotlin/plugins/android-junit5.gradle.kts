
apply(plugin = "de.mannodermaus.android-junit5")

dependencies {
    testImplementation(Testing.Libraries.jUnitApi)
    testRuntimeOnly(Testing.Libraries.jUnitEngine)
    testImplementation(Testing.Libraries.jUnitParams)
}

fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

fun DependencyHandler.testRuntimeOnly(dependencyNotation: Any): Dependency? =
    add("testRuntimeOnly", dependencyNotation)