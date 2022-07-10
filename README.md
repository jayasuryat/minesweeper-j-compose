# Minesweeper w/ `Jetpack Compose`

This is a [Minesweeper](https://en.wikipedia.org/wiki/Minesweeper_(video_game))-like puzzle game, built using [Jetpack Compose](https://developer.android.com/jetpack/compose), for Android.

The objective of this game is to clear a rectangular board containing hidden "mines" or bombs without detonating any of them, with help from clues about the number of neighboring mines in each cell.

##### Try out the app, download the apk from the link below:

[![Minesweeper-JC](https://img.shields.io/badge/Minesweeper--JC-v1.0.0--alpha03-%2306090E?style=for-the-badge&logo=android)](https://github.com/JayaSuryaT/minesweeper-j-compose/releases/download/v1.0.0-alpha03/Minesweeper-jc-v1.0.0-alpha03.apk)


## Demo
https://user-images.githubusercontent.com/37530409/178140932-2cbe1c79-9448-4c7c-af7d-2a15a84dd45b.mov

## Features :
- `Zoomable` and `pannable` minefield
- Safe first click - The first cell is never a `mine` as the `minefield` is generated around the first click
- Automatically `save` and `resume` game progress
- Multiple `difficulty levels` to choose from
- `Settings` screen to update and persist user `preferences`
- `Quick toggle` for click / flag mode
- `Haptic` & `Aural` feedback
- Day / Night theme
- Randomly generated levels

## Controls :
- Tap a `cell` to reveal it
- Long press an `unrevealed cell` to `flag` / `un-flag` it
- Tap an already `revealed cell` to expose potentially `solved cells`
- Change default tap / long-press behaviour from the `quick toggle`

## Package Structure :

 ```
com.jayasuryat.minesweeperjc
├── 📂 app/                          # App module
│   ├── data/                         # Data source mappings
│   ├── di/                           # DI wiring
│   ├── presentation/                 # Navigation & Screens
│   ├── theme/                        # Theming
│   └── MinesweeperApp.kt
│
├── 📂 buildScripts/                 # Build scripts and pre-commit hooks
│
├── 📂 buildSrc/                     # Dependency versions LUT
│
├── 📂 minesweeper-engine/           # Module for driving all the logics of the game
│   ├── controller/                   # Game actions, events, game controller and action handlers
│   │   └── model/                    # Models for actions and events
│   ├── gridgenerator/                # Generators for the minefield
│   └── model/                        # Models for cells and grid
│
├── 📂 minesweeper-engine-debug/     # Module for debug utils related to minesweeper-engine
│
├── 📂 minesweeper-ui/               # Module for all of the UI components of the mine grid
│   ├── action/                       # Action listeners for Minefield interaction actions
│   ├── cell/                         # All composables related to MineCells
│   ├── component/                    # Helper composables
│   ├── config/                       # UI configuration for mine grid
│   ├── grid/                         # All composables related to MineGrid
│   ├── model/                        # UI models for all the MineCells and layout information
│   └── theme/                        # Theming for Minefield UI components
│
├── 📂 data/                         # A Kotlin Multiplatform Mobile module for all of the data operations
│   ├── androidMain/                  # Android implementations
│   │   ├── di/                       # Wiring of Android specific implementations
│   │   └── sqldelight/               # Android Sqlite driver setup
│   ├── iosMain/                      # iOS implementations
│   │   ├── di/                       # Wiring of iOS specific implementations
│   │   └── sqldelight/               # Native Sqlite driver setup
│   ├── commonMain/                   # Common infrastructure
│   │   ├── sqldelight/               # Sqlite query definitions
│   │   └── kotlin/                   
│   │       ├── di/                   # DI wiring for all of the data layer
│   │       ├── model/                # Data models for the data layer
│   │       ├── source/               # Data sources
│   │       └── sqldelight/           # DB setup
│
├── 📂 ui-game/                      # Module for the actual MineField screen
│   ├── composable/                   # All UI components
│   │   ├── feedback/                 # Composables for handling feedback
│   │   ├── toggle/                   # Composables for in game quick-toggle
│   │   └── topbar/                   # Composables for game TopBar
│   ├── data/                         # Data layer skeletons for game state persistence
│   ├── feedback/                     # Helper classes for performing feedback operations
│   ├── logic/                        # Game logic coordinators
│   └── GameScreen.kt                 # Actual Game-Screen
│
├── 📂 ui-difficulty-selection/      # Module for difficulty selection screen
│
└── 📂 util/                         # Module for common utilities
```

## Contributions
Contributions are welcome! See [Contributing Guidelines](https://github.com/JayaSuryaT/minesweeper-j-compose/blob/main/CONTRIBUTING.md).


## Credits
<p>
  <details>
    <summary>
      All the SFX used in this project are sourced from <a href="https://freesound.org/">freesound.org</a> and are licensed under the <a href="https://creativecommons.org/publicdomain/zero/1.0/">Creative Commons 0 License</a>.
    </summary>

* [Cell flagging sound](https://freesound.org/people/plasterbrain/sounds/237422/) by [plasterbrain](https://freesound.org/people/plasterbrain/)
* [Cell unflagging sound](https://freesound.org/people/plasterbrain/sounds/423168/) by [plasterbrain](https://freesound.org/people/plasterbrain/)
* [Game completed sound](https://freesound.org/people/Leszek_Szary/sounds/171584/) by [Leszek_Szary](https://freesound.org/people/Leszek_Szary/)
* [Game over sound](https://freesound.org/people/Leszek_Szary/sounds/171526/) by [Leszek_Szary](https://freesound.org/people/Leszek_Szary/)
* [Cell poping sound](https://freesound.org/people/onikage22/sounds/240566/) by [onikage22](https://freesound.org/people/onikage22/)

  </details>
</p>

## License
```
 Copyright 2022 Jaya Surya Thotapalli

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
