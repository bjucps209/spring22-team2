package model;

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
    Random rand = new Random();

    public Level(int levelSize, int currentLevel){
        ;
        // generateEnemies();
        this.currentLevel = currentLevel;
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
        try{currentScreen = currentScreen.getLeft();}
        catch(NullPointerException n){return;}
    }

    public void goRight(){
        try{currentScreen = currentScreen.getRight();}
        catch(NullPointerException n){return;}
    }

    public void goUp(){
        try{currentScreen = currentScreen.getUp();}
        catch(NullPointerException n){return;}
    }

    public void goDown(){
        try{currentScreen = currentScreen.getDown();}
        catch(NullPointerException n){return;}
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
}