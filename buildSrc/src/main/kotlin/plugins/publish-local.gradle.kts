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

publishing {
    repositories {
        maven { setUrl(mavenLocal()) }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = Artifact.groupId
            version = Artifact.version
            artifactId = project.name
            artifact("${project.buildDir}/outputs/aar/${project.name}-release.aar")
            artifact(sourcesJar)
            pom {
                packaging = "aar"
                withXml {
                    val root = asNode().appendNode("dependencies")
                    addAllDependencyWithScope(root, "compile", "compile")
                    addAllDependencyWithScope(root, "api", "compile")
                    addAllDependencyWithScope(root, "implementation", "runtime")
                    addAllDependencyWithScope(root, "compileOnly", "provided")
                }
            }
        }
    }
}

fun addAllDependencyWithScope(root: Node, scope: String, pomScope: String) {
    configurations.findByName(scope)
        ?.dependencies
        ?.filter { it.group.isNullOrBlank().not() }
        ?.forEach { dependency ->
            root.appendNode("dependency")?.apply {
                appendNode("groupId", dependency.group)
                appendNode("artifactId", dependency.name)
                appendNode("version", dependency.version)
                appendNode("scope", pomScope)
            }
        }
}

fun Project.publishing(configure: PublishingExtension.() -> Unit): Unit =
    (this as ExtensionAware).extensions.configure("publishing", configure)