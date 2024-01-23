
# YouTheSurvivor

**R. Awais J.**\
**RajAwJ@proton.me**


## INTRODUCTION

The game has a simple premise; a player in a hostile environment being hunted by a group of enemies. The objective of the game is to gather the required number of Gems, within the time provided. Eliminating enemies is optional and grants extra benefits.

Once the required number of Gems have been collected, or the Timer has run out, the level automatically clears to the menu screen.


## CLASS STRUCTURE

While the game is based on the MVC (Model-View-Controller) design pattern, the finalized code consists of 5 separate packages with a total of 26 classes. A brief description of the packages is as follows:

* **`main` package:** Consists of the `Model`, `View`, `Controller` and `Start` classes. The control center of the game


* **`templates` package:** Consists of classes that form the basic template of the various objects in the game. Classes include `Player`, `Projectile`, `Scorer`, `Seeker`, `Tile` and `Walker`


* **`util` package:** Consists of classes that consist of the building blocks of the various processes carried out during the game, such as vector operations, or the various constants used in the game. Classes include `Constants`, `GameObject`, `Point3f` and `Vector3f`


* **`extra` package:** The purpose of this package is to make the program more organized and clean, by separating often repeated blocks of code into their own separate classes. Such code usually represents a micro-feature of the game, such as collisions checking, or target seeking. Classes include `Audio`, `EnvironmentCollisionsCheck`, `ExtractSprite`, `ObjectCollisionsCheck`, `RandomWalk`, `SeekTarget` and `StatCheck`


* **`Environment` package:** The purpose of this package is to contain all the general environment building operations in one place. This can include things like Tile management, Audio management and Level management. Classes include `Level`, `ManageAudio`, `StateCheck`, `TilesInteracting` and `TilesNonInteracting`


## ASPECTS OF THE GAME

This section highlights some of the main aspects of the Game.

### 1. Control

The controls are entirely keyboard based. Keys `a`, `d`, `w` and `s` can be used for moving *left*, *right*, *up* and *down*.
`ENTER` key is used to select an option in the *Menu* section.
`SPACE` key is used to release a *projectile*, while within the game. While, `q` can be used to activate *Speed Run*, and key `p` is used to *pause/unpause* the game.

### 2. Levels

There are 4 levels currently available, i.e. **Practice**, **Easy**, **Medium** and **Hard**.

* **Practice** has minimal environment obstacles, an abundance of *Ammo(Fireball)*, and significantly slowed down enemies


* **Easy** & **Medium**, consist of increasing difficulty in terms of time available, ammo available, enemies present and Gems required


* **Hard** level is designed to test the Player’s skills to the limit. There’s few environmental obstacles, that are placed randomly on the map. The enemies are numerous, time is further limited and Gems required to clear the level are increased

### 3. Environment

The environment in this section (unlike the package `Environment`) only refers to the *Tiles* on the map. There are two types of *Tiles*, *interacting* and *non-interacting*.

*Interacting Tiles* line the border of the play area, and exist as obstacles within the play area.
*Non-Interacting Tiles* are the ones that form the general background environment (the *green floor tiles* in this case).

### 4. Player

The **Player** is the figure in *white*, and always starts from the center of the map/play area. The *Player* has certain *’abilities’* at its disposal, that benefit it over it’s enemies. It is slightly faster than the enemies, thus over time can outrun any enemy chasing it. It can activate a `Speed Run` ability, that consumes 4 Gems, which allows it to run many times faster than normal.

Therefore, the **Player’s** speed is a form of defensive action available to it. As an offensive action, it can shoot `Fireballs` at it’s enemies. However, the amount of `Fireballs(Ammo)` available to it is limited, depending on the Level the game is being played at.

### 5. Enemies

There are two types of enemies currently available in the game. The first is the **Walker(Zombie-like NPC)**. It is a *’dumb’* enemy, that roams randomly on the map. However, if the **Player** enters within a certain radius of the **Walker’s** vicinity, the **Walker** will give chase to the **Player** until the **Player** has safely moved outside the said radius.

The second enemy is the **Seeker(Goblin-like NPC)**. It is harder to kill, and slower than the **Walker**. However, it always has the **Player** in its sights, and chases the **Player** until it catches up with it, or is killed.

### 6. Audio

The game’s **Audio** consists of six different sounds, that are divided into two types, i.e. **Music Audio** and **Sound Effects**. The purpose of this division is to differentiate between longer running *background music*, and the shorter format *sound effects* associated with certain in-game actions.

Furthermore, there can only be one **Music Audio** active at any given time, while any number of **Sound Effects** may be active depending on the **Player’s** in-game actions.

**Music Audio** consists of three different sounds:
* **Menu music:** Runs at the main menu
* **Gameplay music:** The normal background music while the game is running 
* **Chase music:** Plays when the **Player** is being chased by a **Walker**

Three different sounds also exist for the **Sound Effects**:
* when a *Fireball(Ammo)* is released (`SPACE` key pressed)
* when a *Fireball* explodes
* when the **Player** takes a *Gem(Score)*.


## ISSUES / LIMITATIONS

While designing the game, certain issues/limitations slowed down its development significantly.

* First, is my own limitation in knowledge of game design/development. As this is the first time I have designed a game, the learning curve was steep specially with little to no help available. Therefore, every part of the game design required a significant investment in time to understand each process, such as *animations*, *panel layout*, *collision detection*, etc.


* Second, while carrying out certain 'heavy' processes, such as *Audio management* and *collision detection*, there were efficiency issues. The game would freeze for a few seconds, at certain times, mainly when processing **Sound Effects**. Though not a major issue, however, this indicates a need for incorporating multi-threading.


* Thirdly, specially with *Audio management*, I did try to include multi-threading but was unsuccessful, given the limited time and difficulty. Furthermore, another major issue was the availability of free-to-use sprites and audio samples.


## Further Information

The game was initially designed as part of a college assignment, however, I later expanded it to include more features and complexity. All the sprites used in the game are freely available online. Some of the base code was provided as part of the original course material, however, most of the game and the concepts behind it are from myself. At the moment I do not intend to further develop the game, but may come back to expand or modify it.

Any modification, distribution or incorporation of the game, or any part of it, may be done with my explicit consent.