# spring22-team2 - Cutting Corners
## Instructions
* Start the game by clicking 'New Game' on the start screen.
* Use WASD to move Up,Left,Down and Right respectively. Multiple keys may be held for diagonal movement.
* Left click to attack in the direction of the cursor.
## Work Completed
### Auxiliary Screens
* Title Screen starts a new game and moves to the other screens where their respective button has been pressed.
* Settings Screen sets variables to be used when starting the game that will be used at a later stage.
* Help Screen has code to display a series of gifs explaining how to play the game. (javafx does not display these currently)
* High Scores Screen displays the current list of High Scores
* About Screen scrolls up like movie credits and displays the work done as well as crediting media sources.

### Basic Game Play
* The Controls noted above move the Player in their respective directions
* Left Clicking triggers an attack.
* Enemies and Obsticles are distributed randomly around the screen.
* Enemies move toward the player when in range
* Enemies die when attacked
* Multi-screen movement is functional (ie the player can move to different segments of the level)

### Serialization
* Serialization methods are implemented for each class and for World.java
* Unit tests are working and testing the first few lines of savegame.dat

### Level Builder
* Background work has been completed

## Known Issues
* The gifs used for displaying controls on the Help Screen do not appear. The Code for this is functional with other gifs for unknown reasons
* Death does not load from last save-point
* Serialization has a few minor bugs in the saving of the entities
## Recording
* [Screen Recording: Title Screen](https://youtu.be/pOgFQCrmpjg)
* [Screen Recording: Gameplay](https://youtu.be/7v2Vsq_bCF0)
## Expenses
|Name|Username|Ownership Area|Hours Completed|Remaining Hours|Journal|
|---|---|---|---|----|---|
|Ethan Collins|EthanCollins02|Auxiliary Screens|34hrs 40min|15hrs 20min|[Ethan's Journal](https://github.com/bjucps209/spring22-team2/wiki/EthanJournal)|
|Paul Alger|PaulAlger05|Serialization|20hrs 15|29hrs 45min|[Paul's Journal](https://github.com/bjucps209/spring22-team2/wiki/PaulJournal)|
|Seth Meyer|smeye584|Level Builder|38 hrs|12 hrs|[Seth's Journal](https://github.com/bjucps209/spring22-team2/wiki/SethJournal)|
|Tripp Lawrence|Tripp312|Basic Game Play|36 hrs 15min|13hrs 45min|[Tripp's Journal](https://github.com/bjucps209/spring22-team2/wiki/TrippJournal)|
