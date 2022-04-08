package model;

import java.util.*;

public class Screen {
    ArrayList<Entity> entities;
    Cell[][] grid = new Cell[8][10];
    Random rand = new Random();

    public Screen(){}

    public void addEnemyGroup(Enemy[] group){
        for (Enemy enemy: group){entities.add(enemy);}
    }

    public void addEntity(Entity entity){entities.add(entity);}

    public void randomize(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 10; j++){
                int random = rand.nextInt(40);
                if (random == 39){grid[i][j] = Cell.rock;}
                else{grid[i][j] = Cell.empty;}
            }
        }
    }
}
