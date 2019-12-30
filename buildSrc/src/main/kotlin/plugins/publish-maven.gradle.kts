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

val artifactVersion: String
    get() = property("version")
        ?.takeIf { it != "unspecified" } as? String ?: AndroidArtifact.version

publishing {
    repositories {
        maven { setUrl(mavenLocal()) }
    }
    publications {
        create<MavenPublication>(project.name) {
            groupId = AndroidArtifact.groupId
            version = artifactVersion
            artifactId = project.name
            artifact("${project.buildDir}/outputs/aar/${project.name}-release.aar")
            artifact(sourcesJar)
            pom {
                packaging = "aar"
                withXml {
                    val dependencies = asNode().appendNode("dependencies")
                    dependencies.add("compile", "compile")
                    dependencies.add("api", "compile")
                    dependencies.add("implementation", "runtime")
                    dependencies.add("compileOnly", "provided")
                    asNode().appendNode("developers").appendNode("developer").apply {
                        appendNode("id", "gocantar")
                        appendNode("name", "Gonzalo Cantarer Perez")
                    }
                    asNode().appendNode("scm").apply {
                        appendNode("url", "https://github.com/gocantar/cassidy")
                    }
                }
            }
        }
    }
}

fun Node.add(scope: String, pomScope: String) {
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
