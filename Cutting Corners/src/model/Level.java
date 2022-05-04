//--------------------------------
// Level.java
// Level defines the group of screens and enemies that the player will travel through
// Defeating the boss at the end of each level advances the player to the next level
//----------------------------------
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import javafx.scene.media.AudioClip;

public class Level {
    private ArrayList<Enemy> totalEnemies;
    private ArrayList<Screen> screens = new ArrayList<Screen>();
    private Screen currentScreen;
    private int currentRow;
    private int currentCol;
    private int currentLevel;
    ScreenObserver observer;
    private AudioClip BOSS_MUSIC = new AudioClip(
            getClass().getResource("/media/Sounds/music/bossfights.mp3").toString());
    private Screen baseScreen;
    Random rand = new Random();

    public Level(int currentLevel) {
        // generateEnemies();
        this.currentLevel = currentLevel;
    }

    public Screen getBaseScreen() {
        return baseScreen;
    }

    public void setBaseScreen(Screen baseScreen) {
        this.baseScreen = baseScreen;
    }

    public void setObserver(ScreenObserver observer) {
        this.observer = observer;
    }

    public ScreenObserver getObserver() {
        return observer;
    }

    public void placeEntity(int row, int col, Entity entity) {
        findScreen(row, col).addEntity(entity);
    }

    // finds a screen in the arraylist @screens in @currentLevel using @row and @col
    // to match with each screen's specific grid coordinates
    public Screen findScreen(int row, int col) {
        for (Screen screen : screens) {
            if (screen.getLocation().getRow() == row && screen.getLocation().getCol() == col) {
                return screen;
            }
        }
        return null;
    }

    // sets the @currentScreen to the @currentScreen's @left parameter, playing
    // music if necessary
    public void goLeft() {
        if (currentScreen.getLeft() != null && !World.instance().isActiveBoss()) {
            Player player = currentScreen.removePlayer();
            player.getCoords().setxCoord(950);
            currentScreen = currentScreen.getLeft();
            currentScreen.addEntity(player);
            if (currentScreen.getFilename().contains("bossroom")) {
                World.instance().setActiveBoss(true);
                World.instance().getMusic().stop();
                World.instance().setMusic(BOSS_MUSIC);
                World.instance().getMusic().play();
            }
            currentCol--;
            observer.Initialize(World.instance().isLoaded());
        }
    }

    // sets the @currentScreen to the @currentScreen's @right parameter, playing
    // music if necessary
    public void goRight() {
        if (currentScreen.getRight() != null && !World.instance().isActiveBoss()) {
            Player player = currentScreen.removePlayer();
            player.getCoords().setxCoord(5);
            currentScreen = currentScreen.getRight();
            currentScreen.addEntity(player);
            if (currentScreen.getFilename().contains("bossroom")) {
                World.instance().setActiveBoss(true);
                World.instance().getMusic().stop();
                World.instance().setMusic(BOSS_MUSIC);
                World.instance().getMusic().play();
            }
            currentCol++;
            observer.Initialize(World.instance().isLoaded());
        }
    }

    // sets the @currentScreen to the @currentScreen's @up parameter, playing music
    // if necessary
    public void goUp() {
        if (currentScreen.getUp() != null && !World.instance().isActiveBoss()) {
            Player player = currentScreen.removePlayer();
            player.getCoords().setyCoord(695);
            currentScreen = currentScreen.getUp();
            currentScreen.addEntity(player);
            if (currentScreen.getFilename().contains("bossroom")) {
                World.instance().setActiveBoss(true);
                World.instance().getMusic().stop();
                World.instance().setMusic(BOSS_MUSIC);
                World.instance().getMusic().play();
            }
            currentRow--;
            observer.Initialize(World.instance().isLoaded());
        }
    }

    // sets the @currentScreen to the @currentScreen's @down parameter, playing
    // music if necessary
    public void goDown() {
        if (currentScreen.getDown() != null && !World.instance().isActiveBoss()) {
            Player player = currentScreen.removePlayer();
            player.getCoords().setyCoord(5);
            currentScreen = currentScreen.getDown();
            currentScreen.addEntity(player);
            if (currentScreen.getFilename().contains("bossroom")) {
                World.instance().setActiveBoss(true);
                World.instance().getMusic().stop();
                World.instance().setMusic(BOSS_MUSIC);
                World.instance().getMusic().play();
            }
            currentRow++;
            observer.Initialize(World.instance().isLoaded());
        }
    }

    // converts the @dummyLevel from the worldBuilder to the actual @currentLevel in
    // World
    public static Level convertDummyLvL(DummyLevel lvl) {

        Level newlvl = new Level(0);
        String[][] adjscrkeeper = new String[lvl.dScreenList.size()][6];

        for (int idx = 0; idx < lvl.getDumScreens().size(); idx++) {
            // screen stuff
            DummyScreen dscr = lvl.getDumScreens().get(idx);
            int[] realID = dscr.getRealID();
            Screen newscr = new Screen(realID[0], realID[1], realID[2], dscr.getBackgroundImgPath());

            if (dscr.screenID.equals(lvl.playerScrStrID))
                newlvl.setCurrentScreen(newscr);

            newlvl.screens.add(newscr);
            adjscrkeeper[idx] = dscr.getAdjacentStringIDs();

            // Objects in screen
            for (int objidx = 0; objidx < dscr.getDumObjects().size(); objidx++) {
                DummyObject dobj = dscr.getDumObj(objidx);
                switch (dobj.type) {
                    case "Obstacle":
                        newscr.setGridSquare(dobj.dimY, dobj.dimX, String.valueOf(Cell.tree));
                        break;
                    case "Player":
                        break;
                    case "Entity":
                        switch (dobj.name) {
                            case "triangle":
                                newscr.addEntity(new Triangle(4, dobj.tlCellX, dobj.tlCellY, newscr));
                                break;
                            case "square":
                                newscr.addEntity(new Square(4, dobj.tlCellX, dobj.tlCellY, newscr));
                                break;
                            case "hexagon":
                                newscr.addEntity(new Hexagon(6, dobj.tlCellX, dobj.tlCellY, newscr));
                                break;
                            case "octagon":
                                newscr.addEntity(new Octagon(8, dobj.tlCellX, dobj.tlCellY, newscr));
                                break;
                            default:
                                newscr.addEntity(new Octagon(1000, dobj.tlCellX, dobj.tlCellY, newscr));
                                break;
                        }
                    case "Boss":
                        switch (dobj.name) {
                            case "pyramidboss":
                                newscr.addEntity(new Pyramid(11, dobj.tlCellX, dobj.tlCellY, newscr));
                                break;
                            case "cube":
                                newscr.addEntity(new Cube(11, dobj.tlCellX, dobj.tlCellY, newscr));
                                break;
                            case "dodeca":
                                newscr.addEntity(new Dodecahedron(11, dobj.tlCellX, dobj.tlCellY, newscr));
                                break;
                            default:
                                break;
                        }
                    case "Item":
                        UsableItem bleh = new UsableItem("Health Potion", 2, 1, 9999999, 0, 5, 0,
                                "media/Player/Bow.png");
                        newscr.addEntity(new DroppedItem(200, 500, bleh, "media/Player/Item.png", 100, newscr));
                        break;
                    default:
                        break;
                }

            }

        }

        for (int adjstridx = 0; adjstridx < adjscrkeeper.length; adjstridx++) {
            Screen curscr = newlvl.getScreens().get(adjstridx);

            int[] upid = DummyScreen.strIDtoRealID(adjscrkeeper[adjstridx][0]);
            if (upid != null)
                curscr.setUp(newlvl.findScreen(upid[1], upid[0]));

            int[] downid = DummyScreen.strIDtoRealID(adjscrkeeper[adjstridx][1]);
            if (downid != null)
                curscr.setDown(newlvl.findScreen(downid[1], downid[0]));

            int[] rightid = DummyScreen.strIDtoRealID(adjscrkeeper[adjstridx][2]);
            if (rightid != null)
                curscr.setRight(newlvl.findScreen(rightid[1], rightid[0]));

            int[] leftid = DummyScreen.strIDtoRealID(adjscrkeeper[adjstridx][3]);
            if (leftid != null)
                curscr.setLeft(newlvl.findScreen(leftid[1], leftid[0]));
        }

        newlvl.getCurrentScreen().addEntity(new Player(100, 100));

        return newlvl;
    }

    // Getters and Setters ----------------------------

    public ArrayList<Screen> getScreens() {
        return screens;
    }

    public void setScreens(ArrayList<Screen> screens) {
        this.screens = screens;
    }

    public void addScreen(Screen screen) {
        screens.add(screen);
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public ArrayList<Enemy> getTotalEnemies() {
        return totalEnemies;
    }

    public void setTotalEnemies(ArrayList<Enemy> totalEnemies) {
        this.totalEnemies = totalEnemies;
    }

    public void setCurrentScreen(Screen currentScreen) {
        World.instance().setPreviousScreen(this.currentScreen);
        this.currentScreen = currentScreen;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }


    /**
     * Saves the state of this class with the necessary variables to a binary file
     * @param file
     * @throws IOException
     */
    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(currentLevel);
        file.writeInt(screens.size());

        currentScreen.serialize(file);

        // for (Screen s : screens) {
        // s.serialize(file);
        // }

        file.writeInt(currentRow);
        file.writeInt(currentCol);

    }

    /**
     * Factory method
     * Reads the variables left in the file by serialize.
     * Creates an instance of this class using those variables.
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static Level deserialize(DataInputStream file) throws IOException {
        int currentLevel = file.readInt();
        Level lvl = World.instance().getCurrentLevel();
        Screen screen = lvl.getCurrentScreen();

        int numScreens = file.readInt();

        Screen screen2 = Screen.deserialize(file);
        screen.setEntities(screen2.getEntities());
        screen.setGrid(screen2.getGrid());
        lvl.setCurrentScreen(screen);

        // for (int i = 0; i < numScreens; ++i) {
        // Screen s = Screen.deserialize(file);
        // lvl.getScreens().(s);
        // }

        lvl.setCurrentRow(file.readInt());
        lvl.setCurrentCol(file.readInt());

        return lvl;
    }

}