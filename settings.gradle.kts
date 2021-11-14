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
include(":minesweeper-engine")
include(":minesweeper-ui")
include(":minesweeper-engine-debug")
include(":ui-game")
