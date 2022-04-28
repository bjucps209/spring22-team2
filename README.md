# spring22-team2 - Cutting Corners
## Instructions
* Start the game by clicking 'New Game' on the start screen.
* Use WASD to move Up,Left,Down and Right respectively. Multiple keys may be held for diagonal movement.
* Left click to attack in the direction of the cursor.
* Press ESC to pause the game.
## Work Completed
### Auxiliary Screens
* Title Screen starts a new game and moves to the other screens where their respective button has been pressed.
* Settings Screen sets variables to be used when starting the game that will be used at a later stage.
* Help Screen has code to display a series of gifs explaining how to play the game.
* High Scores Screen displays the current list of High Scores
* About Screen scrolls up like movie credits and displays the work done as well as crediting media sources.

### Basic Game Play
* The Controls noted above move the Player in their respective directions
* Left Clicking triggers an attack.
* Enemies and Obsticles are distributed randomly around the screen.
* Enemies move toward the player when in range
* Enemies die when attacked
* Multi-screen movement is functional (ie the player can move to different segments of the level)
* All enemies are present
* All bosses are present
* Health for both enemies and players is displayed
* Score is tracked
* Pausing and pause screen are functional

### Serialization
* Serialization methods are implemented for each class and for World.java
* Saving is functional
* Unit tests are working and testing the first few lines of savegame.dat
* Loading is still full of bugs

### Level Builder
* GUI is functional
* Game Files are being saved properly

## Known Issues
* Death does not load from last save-point
* Secret Boss does not move
* The walking animation does not continue playing when walking between screens
* When dealing the final blow to an enemy, the attack animation does not play
* World.instance().setDifficulty() does not set the difficulty to the parameterized integer
* High Scores aren't being tracked on death
* Loading systems aren't functional
## Recording
* [Screen Recording: Gameplay](https://bju.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=b457c1cb-daaa-468e-b43e-ae8500656be0)
* [Screen Recording: Level Builder](* [Screen Recording: Gameplay](https://bju.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=7d0d4b5b-0f21-4e28-b612-ae85005fcec5))

## Expenses
|Name|Username|Ownership Area|Hours Completed|Remaining Hours|Journal|
|---|---|---|---|----|---|
|Ethan Collins|EthanCollins02|Auxiliary Screens|50hrs|0hrs|[Ethan's Journal](https://github.com/bjucps209/spring22-team2/wiki/EthanJournal)|
|Paul Alger|PaulAlger05|Serialization|33hrs 15|21hrs 45min|[Paul's Journal](https://github.com/bjucps209/spring22-team2/wiki/PaulJournal)|
|Seth Meyer|smeye584|Level Builder|38 hrs|12 hrs|[Seth's Journal](https://github.com/bjucps209/spring22-team2/wiki/SethJournal)|
|Tripp Lawrence|Tripp312|Basic Game Play|40 hrs 45min|9hrs 15min|[Tripp's Journal](https://github.com/bjucps209/spring22-team2/wiki/TrippJournal)|
