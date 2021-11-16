dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}

rootProject.name = "Minesweeper-JC"

include(":app")

include(":util")

// Feature
include(":ui-game")

// Core
include(":core:minesweeper-engine")
include(":core:minesweeper-ui")
include(":core:minesweeper-engine-debug")

// Project definitions
private val root = rootProject.projectDir.path
project(":core:minesweeper-engine").projectDir = File("$root/minesweeper-engine")
project(":core:minesweeper-ui").projectDir = File("$root/minesweeper-ui")
project(":core:minesweeper-engine-debug").projectDir = File("$root/minesweeper-engine-debug")