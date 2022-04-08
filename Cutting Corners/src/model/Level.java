package model;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class Level {
    ArrayList<Enemy> totalEnemies;
    Screen[][] screens;
    Screen currentScreen;
    int currentRow;
    int currentCol;
    int currentLevel;
    Random rand = new Random();

    public Level(int levelSize, int currentLevel){
        screens = new Screen[levelSize][levelSize];
        // generateEnemies();
        this.currentLevel = currentLevel;
    }

    public void changeCurrentScreen(){
        currentScreen = screens[currentRow][currentCol];
    }

    public void placeEntity(int row, int col, Entity entity){
        screens[row][col].addEntity(entity);
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
        currentCol--;
        changeCurrentScreen();
    }

    public void goRight(){
        currentCol++;
        changeCurrentScreen();
    }

    public void goUp(){
        currentRow--;
        changeCurrentScreen();
    }

    public void goDown(){
        currentRow++;
        changeCurrentScreen();
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


}