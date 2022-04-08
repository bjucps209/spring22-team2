package model;

import java.util.*;

public class Screen {
    ArrayList<Entity> entities = new ArrayList<Entity>();
    Cell[][] grid;
    Random rand = new Random();

    public Screen(){
        grid = new Cell[10][15];
        randomize();
    }

    public void addEnemyGroup(Enemy[] group){
        for (Enemy enemy: group){entities.add(enemy);}
    }

    public void addEntity(Entity entity){entities.add(entity);}

    public void randomize(){
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 15; j++){
                int random = rand.nextInt(40);
                if (random == 39){grid[i][j] = Cell.rock;}
                else{grid[i][j] = Cell.empty;}
            }
        }
    }

    public ArrayList<Obstacle> findObstacles(){
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 15; j++){
                if (grid[i][j] != Cell.empty){
                    Obstacle obstacle = new Obstacle(i, j, grid[i][j]);
                    obstacles.add(obstacle);
                }
            }
        }
        return obstacles;
    }
}
