
apply(plugin = "de.mannodermaus.android-junit5")

dependencies {
    testImplementation(Testing.Libraries.jUnit5Api)
    testRuntimeOnly(Testing.Libraries.jUnit5Engine)
    testImplementation(Testing.Libraries.jUnit5Params)
}

fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

fun DependencyHandler.testRuntimeOnly(dependencyNotation: Any): Dependency? =
    add("testRuntimeOnly", dependencyNotation)