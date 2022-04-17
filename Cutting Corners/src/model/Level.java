package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class Level {
    ArrayList<Enemy> totalEnemies;
    public ArrayList<Screen> screens = new ArrayList<Screen>();
    Screen currentScreen;
    int currentRow;
    int currentCol;
    int currentLevel;
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
        if (currentScreen.getLeft() != null){
            Player player = currentScreen.removePlayer();
            player.coords.setxCoord(950);
            currentScreen = currentScreen.getLeft();
            currentScreen.addEntity(player);
            currentCol--;
            observer.Initialize();
        }
    }

    public void goRight(){
        if (currentScreen.getRight() != null){
            Player player = currentScreen.removePlayer();
            player.coords.setxCoord(5);
            currentScreen = currentScreen.getRight();
            currentScreen.addEntity(player);
            currentCol++;
            observer.Initialize();
        }
    }

    public void goUp(){
        if (currentScreen.getUp() != null){
            Player player = currentScreen.removePlayer();
            player.coords.setyCoord(695);
            currentScreen = currentScreen.getUp();
            currentScreen.addEntity(player);
            currentRow--;
            observer.Initialize();
        }
    }

    public void goDown(){
        if (currentScreen.getDown() != null){
            Player player = currentScreen.removePlayer();
            player.coords.setyCoord(5);
            currentScreen = currentScreen.getDown();
            currentScreen.addEntity(player);
            currentRow++;
            observer.Initialize();
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



    public void serialize(DataOutputStream file) throws IOException {
        //save the level data
    
    }

    public void deserialize(DataInputStream file) throws IOException {
        
    }

}