# Minesweeper w/ `Jetpack Compose`

This is a [Minesweeper](https://en.wikipedia.org/wiki/Minesweeper_(video_game))-like puzzle game, built using [Jetpack Compose](https://developer.android.com/jetpack/compose), for Android.

The objective of this game is to clear a rectangular board containing hidden "mines" or bombs without detonating any of them, with help from clues about the number of neighboring mines in each cell.

## Demo
| Dark mode | Light mode | 
| -- | -- |
| <img src="https://github.com/JayaSuryaT/minesweeper-j-compose/raw/main/art/Main_DarkTheme.gif" alt="Using Views - Light theme" data-canonical-src="https://github.com/JayaSuryaT/minesweeper-j-compose/raw/main/art/Main_DarkTheme.gif" width="270" height="585" />|<img src="https://github.com/JayaSuryaT/minesweeper-j-compose/raw/main/art/Main_LightTheme.gif" alt="Using Views - Dark theme" data-canonical-src="https://github.com/JayaSuryaT/minesweeper-j-compose/raw/main/art/Main_LightTheme.gif" width="270" height="585" />|


## Features : 
* Zoomable and Panabel minefield
* Safe first click - The first cell is never a mine as the minefield is generated around the first click
* Multiple difficulty levels to choose from
* Haptic & Aural feedback
* Day / Night theme
* Randomly generated levels

## Controls : 
* Tap a cell to reveal it
* Long press an unrevealed cell to flag/unflag it
* Tap an already revealed cell to expose potentially solved cells

## Package Structure :

 ```
com.jayasuryat.minesweeperjc
├── 📂 app/                          # App module
│   ├── presentation/                 # Navigation & Screens
│   ├── theme/                        # Theming
│   └── MinesweeperApp.kt    
│
├── 📂 minesweeper-engine/           # Module for driving all the logics of the game
│   ├── controller/                   # Game actions, events, game controller and action handlers
│   │   └── model/                    # Models for actions and events
│   ├── gridgenerator/                # Generators for the minefield
│   ├── model/                        # Models for cells and grid
│   ├── state/                        # Stateful grid and utils
│   └── util/
│
├── 📂 minesweeper-engine-debug/     # Module for debug utils related to minesweeper-engine
│
├── 📂 minesweeper-ui/               # Module for all of the UI components of the mine grid
│   └── composable/
│       ├── action/                   # Action listeners for Minefield interaction actions
│       ├── cell/                     # All composables related to MineCells
│       ├── component/                # Helper composables
│       ├── grid/                     # All composables related to MineGrid
│       └── theme/                    # Theming for Minefield UI components
│
├── 📂 ui-game/                      # Module for the actual MineField screen
│   ├── composable/                   # All UI components
│   │   ├── feedback/                 # Composables for handling feedback
│   │   └── topbar/                   # Composables for game TopBar
│   ├── feedback/                     # Helper classes for performing feedback operations
│   ├── logic/                        # Game logic coordinators
│   └── GameScreen.kt                 # Actual Game-Screen
│
├── 📂 ui-difficulty-selection/      # Module for difficulty selection screen
│
└── 📂 util/                         # Module for common utilities
```


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

