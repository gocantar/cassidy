rootProject.name = "Cassidy"

// Library modules
include(":core")
include(":network")
include(":test")
include(":tools")
include(":widgets")

// Example App modules
include(":app")
include(":timer")
include(":teams")

project(":timer").projectDir = File(rootDir, "./examples/timer")
project(":teams").projectDir = File(rootDir, "./examples/teams")
