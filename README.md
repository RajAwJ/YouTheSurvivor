--------------------
- YOU THE SURVIVOR -
--------------------

------------------------------
INTRODUCTION
------------------------------

The game has a simple premise; a player in a hostile environment being hunted by a group of enemies. The objective of the game is to gather the required number of Gems, within the time provided. Eliminating enemies is optional and grants extra benefits.

Once the required number of Gems have been collected, or the Timer has run out, the level automatically clears to the menu screen.

------------------------------
CLASS STRUCTURE
------------------------------

While the game is based on the base MVC (Model-View-Controller) class structure provided in the example code, the finalized code consists of 5 separate packages with a total of 26 classes. A brief description of the packages is as follows:

	* ’main’ package: Consists of the 'Model', 'View', 'Controller' and 'Start' classes. The control center of the game.

	* ’templates’ package: Consists of classes that form the basic template of the various objects in the game. Classes include 'Player', 'Projectile', 'Scorer', 'Seeker', 'Tile' and 'Walker'.

	* ’util’ package: Consists of classes that consist of the building blocks of the various processes carried out during the game, such as vector operations, or the various constants used in the game. A lot of this is unchanged from the base code provided for this Game by the school. Classes include 'Constants', 'GameObject', 'Point3f' and 'Vector3f'.

	* ’extra’ package: The purpose of this package is to make the program more organized and clean, by separating often repeated blocks of code into their own separate classes. Such code usually represents a micro-feature of the game, such as collisions checking, or target seeking. Classes include 'Audio', 'EnvironmentCollisionsCheck', 'ExtractSprite', 'ObjectCollisionsCheck', 'RandomWalk', 'SeekTarget' and 'StatCheck'.

	* ’Environment’ package: The purpose of this package is to contain all of the general environment building operations in one place. This can include things like Tile management, Audio management and Level management. Classes include 'Level', 'ManageAudio', 'StateCheck', 'TilesInteracting' and 'TilesNonInteracting'.

------------------------------
ASPECTS OF THE GAME
------------------------------

This section highlights some of the main aspects of the Game.

a). Control

The controls are entirely keyboard based. Keys ’a’, ’d’, ’w’ and ’s’ can be used for moving left, right, up and down.
’ENTER’ key is used to select an option in the Menu section.
’SPACE’ key is used to release a projectile, while within the game. While, ’q’ can be used to activate ’Speed Run, and key ’p’ is used to pause/unpause the game.

b). Levels

There are 4 levels currently available, i.e. Practice, Easy, Medium and Hard.
’Practice’ has minimal environment obstacles, an abundance of Ammo(Fireball), and significantly slowed down enemies.
’Easy’ & ’Medium’, consist of increasing difficulty in terms of time available, ammo available, enemies present and Gems required.
’Hard’ level is designed to test the Player’s skills to the limit. There’s few environmental obstacles, that are placed randomly on the map. The enemies are numerous, time is further limited and Gems required to clear the level is increased.

c). Environment

The environment in this section (unlike the package Environment) only refers to the Tiles on the map. There are two types of Tiles, interacting and non-interacting.
Interacting Tiles line the border of the play area, and exist as obstacles within the play area.
Non-Interacting Tiles are the ones that form the general background environment (the green floor tiles in this case).

d). Player

The Player is the figure in white, and always starts from the center of the map/play area. The Player has certain ’abilities’ at its disposal, that benefit it over it’s enemies. It is slightly faster than the enemies, thus over time can outrun any enemy chasing it. It can activate a ’Speed Run’ ability, that consumes 4 Gems, which allows it to run many times faster than normal. Therefore, the Player’s speed is a form of defensive action available to it. As an offensive action, it can shoot Fireballs at it’s enemies. However, the amount of Fireballs(Ammo) available to it is limited, depending on the Level the game is being played at.

e). Enemies

There are two types of enemies currently available in the game. The first is the Walker(Zombie-like NPC). It is a ’dumb’ enemy, that roams randomly on the map. However, if the Player enters within a certain radius of the Walker’s vicinity, the Walker will give chase to the Player until the Player has safely moved outside the said radius.
The second enemy is the Seeker(Goblin-like NPC). It is harder to kill, and slower than the Walker. However, it always has the Player in its sights, and chases the Player until it catches up with it or is killed.

f). Audio

The game’s Audio consists of six different sounds, that are divided into two types, i.e. Music audio and Sound Effects. The purpose of this division is to differentiate between longer running background music, and the shorter format sound effects associated with certain in-game actions. Furthermore, there can only be one Music audio active at any given time, while any number of Sound Effects may be active depending on the Player’s in-game actions.

Music audio consists of three different sounds, i.e. Menu music, Gameplay music and Chase music. Menu music is run at the main menu, Gameplay music is the normal background music while the game is running, and Chase music plays when the Player is being chased by a Walker.
Three different sounds also exist for the Sound Effects, i.e. when a Fireball(Ammo) is released (SPACE key pressed), when a Fireball explodes, and when the Player takes a Gem(Score).

------------------------------
CODE
------------------------------

In this section, I would like to present some of the game mechanisms I believe are the highlights of the game’s program.

	* 'ExtractSprite' class, which provides a means to extract the sprites of in-game objects, is a concept that required a lot of thinking, along with trial and error. While the basic idea is simple, i.e. to iterate over a series of images in sequence to create a certain animation. However, the issue came when omitting certain parts of the original image series, to include only certain parts of a row or column of the image series. This is most evident when extracting the sprite of the Player. The overloaded method ExtractSprite() inside this class is designed to allow for such a manipulation, where if necessary certain rows or columns(or parts of them) of the image-in-question can be ignored.

	* 'EnvironmentCollisionsCheck' and 'ObjectCollisionsCheck' are two classes dealing with collisions within the game. They, however, have slightly distinct ways of computing collision detection.

'ObjectCollisionsCheck' only checks for collisions between instances of non-environment objects, such as
Player-Walker collision. It does this by checking for overlap between the rectangular bounds of these objects.

For collisions between Objects(non-environment) and Tiles(interacting), a different method is used keeping in mind efficiency and processing speed. There are ’interacting’ Tiles lining the border and more inside the game area, forming obstacles. Thus, it is impractical to create an object instance for all of them. Therefore, a workaround I implemented, is to create a HashMap<Tile, Point3f[]> to represent the ’interacting’ Tiles while detecting collisions.

When the map is generated, a list of positions for each type of ’interacting’ Tile is created. However,
unlike objects such as the Fireball, only one instance of each Tile type is created. When checking for collisions, each position of the Tile is iterated through, and that instance is then given that position. Therefore, when checking for collisions with other objects, just one instance of the Tile type is enough, regardless of where the collision happens.

	* All objects within the game inherit from the 'GameObject' class, which provides a basic template to design objects on. Thus, all game operations are simpler to carry out and follow a pattern/mechanism.

	* Furthermore, as a result of the object structure and mechanism of checking interactions, the program is modular and can be further scaled by adding in new features, or different types of objects whenever needed.

------------------------------
ISSUES / LIMITATIONS
------------------------------

While designing the game, certain issues/limitations slowed down its development significantly. Firstly, is my own limitation in knowledge of game design/development. As this is the first time I have designed a game, the learning curve was steep specially with little to no help available. Therefore, every part of the game design required a significant investment in time to understand each process, such as animations, panel layout, collision detection, etc.

Secondly, while carrying out certain ’heavy’ processes, such as Audio management and collisions checking, there were efficiency issues. The game would freeze for a few seconds, at certain times, mainly when processing Sound Effects. Though not a major issue, however, this indicates a need for incorporating multi-threading.

Thirdly, specially with Audio management, I did try to include multi-threading but was unsuccessful, given the limited time and difficulty. Furthermore, another major issue was the availability of free-to-use sprites and audio samples.


