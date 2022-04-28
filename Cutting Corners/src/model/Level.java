package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class Level {
    private ArrayList<Enemy> totalEnemies;
    private ArrayList<Screen> screens = new ArrayList<Screen>();
    private Screen currentScreen;
    private int currentRow;
    private int currentCol;
    private int currentLevel;
    ScreenObserver observer;
    Random rand = new Random();

    public Level(int currentLevel){
        // generateEnemies();
        this.currentLevel = currentLevel;
    }

    public void setObserver(ScreenObserver observer){
        this.observer = observer;
    }

    public ScreenObserver getObserver(){
        return observer;
    }

    public void placeEntity(int row, int col, Entity entity){
        findScreen(row, col).addEntity(entity);
    }

    public Screen findScreen(int row, int col){
        for (Screen screen: screens){
            if (screen.getLocation().getRow() == row && screen.getLocation().getCol() == col){
                return screen;
            }
        }
        return null;
    }

    // private void generateEnemies(){
    //     int totalSize = currentLevel * 20 + 40;
    //     while (true){
    //         int size = rand.nextInt(currentLevel + 2) + 1;
    //         int sides = rand.nextInt(currentLevel) + 3;
    //         if (size >= totalSize){size = totalSize; break;}
    //         else{totalSize -= size;}
    //         Enemy enemy = new Enemy(sides, size);
    //         totalEnemies.add(enemy);
    //     }
    //     distributeEnemies();
    // }

    // private void distributeEnemies(){
        // ListIterator<Enemy> iterator = totalEnemies.listIterator();

        // while (true){
        //     int groupSize = rand.nextInt(4) + 1;
        //     Enemy[] group = iterate(groupSize, iterator);
        //     //int row = rand.nextInt(LEVEL_WIDTH);
        //     //int col = rand.nextInt(LEVEL_HEIGHT);
        //     //if (screens[row][col].full){continue;}
        //     //else{screens[row][col].addEntityGroup(group);}
        // }
    // }

    public void goLeft(){
        if (currentScreen.getLeft() != null&&!World.instance().isActiveBoss()){
            Player player = currentScreen.removePlayer();
            player.getCoords().setxCoord(950);
            currentScreen = currentScreen.getLeft();
            currentScreen.addEntity(player);
            if(currentScreen.getFilename().contains("bossroom"))
            {
                World.instance().setActiveBoss(true);
            }
            currentCol--;
            observer.Initialize(World.instance().isLoaded());
        }
    }

    public void goRight(){
        if (currentScreen.getRight() != null&&!World.instance().isActiveBoss()){
            Player player = currentScreen.removePlayer();
            player.getCoords().setxCoord(5);
            currentScreen = currentScreen.getRight();
            currentScreen.addEntity(player);
            if(currentScreen.getFilename().contains("bossroom"))
            {
                World.instance().setActiveBoss(true);
            }
            currentCol++;
            observer.Initialize(World.instance().isLoaded());
        }
    }

    public void goUp(){
        if (currentScreen.getUp() != null&&!World.instance().isActiveBoss()){
            Player player = currentScreen.removePlayer();
            player.getCoords().setyCoord(695);
            currentScreen = currentScreen.getUp();
            currentScreen.addEntity(player);
            if(currentScreen.getFilename().contains("bossroom"))
            {
                World.instance().setActiveBoss(true);
            }
            currentRow--;
            observer.Initialize(World.instance().isLoaded());
        }
    }

    public void goDown(){
        if (currentScreen.getDown() != null&&!World.instance().isActiveBoss()){
            Player player = currentScreen.removePlayer();
            player.getCoords().setyCoord(5);
            currentScreen = currentScreen.getDown();
            currentScreen.addEntity(player);
            if(currentScreen.getFilename().contains("bossroom"))
            {
                World.instance().setActiveBoss(true);
            }
            currentRow++;
            observer.Initialize(World.instance().isLoaded());
        }
    }

    public Enemy[] iterate(int groupSize, ListIterator<Enemy> iterator){
        if (totalEnemies.size() - iterator.nextIndex() < groupSize){
            groupSize = totalEnemies.size() - iterator.nextIndex();
        }

        Enemy[] group = new Enemy[groupSize];

        for (int i = 0; i < groupSize; i++){
            if (! iterator.hasNext()){return null;}
            group[i] = (Enemy) iterator.next();
        }

        return group;
    }

    public static Level convertDummyLvL(DummyLevel lvl) {
        Level newlvl = new Level(0);
        String[][] adjscrkeeper = new String[lvl.dScreenList.size()][6]; 

        for (int idx = 0; idx < lvl.getDumScreens().size(); idx++) {
            //screen stuff
            DummyScreen dscr = lvl.getDumScreens().get(idx);
            int[] realID = dscr.getRealID();
            Screen newscr = new Screen(realID[0], realID[1], realID[2], dscr.getBackgroundImgPath());

            if (dscr.screenID.equals(lvl.playerScrStrID)) newlvl.setCurrentScreen(newscr);

            newlvl.screens.add(newscr);
            adjscrkeeper[idx] = dscr.getAdjacentStringIDs();

            //Objects in screen
            for (int objidx = 0; objidx < dscr.getDumObjects().size(); objidx++) {
                DummyObject dobj = dscr.getDumObj(objidx);
                switch(dobj.type) {
                    case "Obstacle":
                        newscr.setGridSquare(dobj.dimY, dobj.dimX, String.valueOf(Cell.tree));
                        break;
                    case "Player":
                        break;
                    case "Entity":
                        switch(dobj.name) {
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
                                newscr.addEntity(new Octagon(1000, dobj.tlCellX,  dobj.tlCellY, newscr));
                                break;
                        }
                    case "Boss":
                        switch(dobj.name) {
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
                        UsableItem bleh = new UsableItem("Health Potion", 2, 1, 9999999, 0, 5, 0, "media/Player/Bow.png");
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
            if(upid != null) curscr.setUp(newlvl.findScreen(upid[1], upid[0]));

            int[] downid = DummyScreen.strIDtoRealID(adjscrkeeper[adjstridx][1]);
            if(downid != null)curscr.setDown(newlvl.findScreen(downid[1], downid[0]));

            int[] rightid = DummyScreen.strIDtoRealID(adjscrkeeper[adjstridx][2]);
            if(rightid != null)curscr.setRight(newlvl.findScreen(rightid[1], rightid[0]));

            int[] leftid = DummyScreen.strIDtoRealID(adjscrkeeper[adjstridx][3]);
            if(leftid != null)curscr.setLeft(newlvl.findScreen(leftid[1], leftid[0]));
        }

        return newlvl;
    } 

    // Getters and Setters ----------------------------
    
    public ArrayList<Screen> getScreens() {
        return screens;
    }

    public void setScreens(ArrayList<Screen> screens) {
        this.screens = screens;
    }

    public void addScreen(Screen screen){
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

    public Screen getCurrentScreen(){
        return currentScreen;
    }
    
    public ArrayList<Enemy> getTotalEnemies() {
        return totalEnemies;
    }

    public void setTotalEnemies(ArrayList<Enemy> totalEnemies) {
        this.totalEnemies = totalEnemies;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(currentLevel);
        // file.writeInt(totalEnemies.size());
        // for (Enemy e : totalEnemies) {
        //     e.serialize(file);
        // }
        file.writeInt(screens.size());
        for (Screen s : screens) {
            s.serialize(file);
        }
        file.writeInt(currentRow);
        file.writeInt(currentCol);
    
    }

    public static Level deserialize(DataInputStream file) throws IOException {
        int currentLevel = file.readInt();
        Level lvl = new Level(currentLevel);

        // int numEnemies = file.readInt();
        // for (int i = 0; i < numEnemies; ++i) {
        //     Enemy e = Enemy.deserialize(file);
        //     lvl.getTotalEnemies().add(e);
        // }
        int numScreens = file.readInt();
        for (int i = 0; i < numScreens; ++i) {
            Screen s = Screen.deserialize(file);
            lvl.getScreens().add(s);
        }
        lvl.setCurrentRow(file.readInt());
        lvl.setCurrentCol(file.readInt());

        return lvl;
    }

}