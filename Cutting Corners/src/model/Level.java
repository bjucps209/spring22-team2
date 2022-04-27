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
    private Screen baseScreen;
    Random rand = new Random();

    public Level(int currentLevel){
        // generateEnemies();
        this.currentLevel = currentLevel;
    }

    

    public Screen getBaseScreen() {
        return baseScreen;
    }



    public void setBaseScreen(Screen baseScreen) {
        this.baseScreen = baseScreen;
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

        file.writeInt(screens.size());

        currentScreen.serialize(file);

        for (Screen s : screens) {
            s.serialize(file);
        }

        file.writeInt(currentRow);
        file.writeInt(currentCol);
    
    }

    public static Level deserialize(DataInputStream file) throws IOException {
        int currentLevel = file.readInt();
        Level lvl = new Level(currentLevel);

        int numScreens = file.readInt();

        Screen screen = Screen.deserialize(file);
        lvl.setCurrentScreen(screen);
        lvl.getScreens().add(screen);

        for (int i = 0; i < numScreens; ++i) {
            Screen s = Screen.deserialize(file);
            lvl.getScreens().add(s);
        }

        lvl.setCurrentRow(file.readInt());
        lvl.setCurrentCol(file.readInt());

        return lvl;
    }

}