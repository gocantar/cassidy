object Artifact {

    const val groupId = "com.gocantar.cassidy"
    const val version = "1.0.0-alpha1"

    data class Local(val artifactId: String, val version: String) {
        val groupId = Artifact.groupId
    }
}