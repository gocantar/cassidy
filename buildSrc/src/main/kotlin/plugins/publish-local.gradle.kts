import groovy.util.Node

/**
 * This plugin publish a module as local artifact
 */

apply(plugin = "maven-publish")

val sourcesJar by tasks.run {
    creating(Jar::class) {
        archiveClassifier.set("sources")
        archiveClassifier.convention("sources")
        from("android.sourceSets.main.java.srcDirs")
    }
}

val artifact: Artifact.Local by lazy {
    val version = property("version")
        ?.takeIf { it != "unspecified" } ?: Artifact.version
    Artifact.Local(project.name, version as String)
}

publishing {
    repositories {
        maven { setUrl(mavenLocal()) }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = artifact.groupId
            version = artifact.version
            artifactId = artifact.artifactId
            artifact("${project.buildDir}/outputs/aar/${project.name}-release.aar")
            artifact(sourcesJar)
            pom {
                packaging = "aar"
                withXml {
                    val root = asNode().appendNode("dependencies")
                    root.addAllDependencyWithScope("compile", "compile")
                    root.addAllDependencyWithScope("api", "compile")
                    root.addAllDependencyWithScope("implementation", "runtime")
                    root.addAllDependencyWithScope("compileOnly", "provided")
                }
            }
        }
    }
}

fun Node.addAllDependencyWithScope(scope: String, pomScope: String) {
    configurations.findByName(scope)
        ?.dependencies
        ?.filter { it.group.isNullOrBlank().not() }
        ?.forEach { dependency ->
            appendNode("dependency")?.apply {
                appendNode("groupId", dependency.group)
                appendNode("artifactId", dependency.name)
                appendNode("version", dependency.version)
                appendNode("scope", pomScope)
            }
        }
}

fun Project.publishing(configure: PublishingExtension.() -> Unit): Unit =
    (this as ExtensionAware).extensions.configure("publishing", configure)