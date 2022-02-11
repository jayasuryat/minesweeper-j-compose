@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Minesweeper-JC"

include(":app")

include(":util")

include(":data")

// Feature
include(":ui-game")
include(":ui-difficulty-selection")
include(":ui-settings")

// Core
include(":core:minesweeper-engine")
include(":core:minesweeper-ui")
include(":core:minesweeper-engine-debug")

// Project definitions
private val root = rootProject.projectDir.path
project(":core:minesweeper-engine").projectDir = File("$root/minesweeper-engine")
project(":core:minesweeper-ui").projectDir = File("$root/minesweeper-ui")
project(":core:minesweeper-engine-debug").projectDir = File("$root/minesweeper-engine-debug")