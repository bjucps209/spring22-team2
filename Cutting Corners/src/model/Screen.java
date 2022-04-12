package model;

import java.util.*;

public class Screen {
    ArrayList<Entity> entities = new ArrayList<Entity>();
    Cell[][] grid;
    Location location;
    Screen left;
    Screen right;
    Screen up;
    Screen down;
    Random rand = new Random();

    public Screen(int row, int col, int level){
        location = new Location(row, col, level);
        grid = new Cell[7][13];
        randomize();
    }

    public Location getLocation(){
        return location;
    }

    public void addEnemyGroup(Enemy[] group){
        for (Enemy enemy: group){entities.add(enemy);}
    }

    public void addEntity(Entity entity){entities.add(entity);}

    public void randomize(){
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 13; j++){
                int random = rand.nextInt(40);
                if (random == 39){grid[i][j] = Cell.rock;}
                else{grid[i][j] = Cell.empty;}
            }
        }
    }

    public ArrayList<Obstacle> findObstacles(){
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 13; j++){
                if (grid[i][j] != Cell.empty){
                    Obstacle obstacle = new Obstacle(i, j, grid[i][j]);
                    obstacles.add(obstacle);
                }
            }
        }
        return obstacles;
    }

    public Player removePlayer(){
        for (Entity entity: entities){
            if (entity instanceof Player){
                entities.remove(entity);
                return (Player) entity;
            }
        }
        return null;
    }

    public Screen getLeft() {
        return left;
    }

    public void setLeft(Screen left) {
        this.left = left;
    }

    public Screen getRight() {
        return right;
    }

    public void setRight(Screen right) {
        this.right = right;
    }

    public Screen getUp() {
        return up;
    }

    public void setUp(Screen up) {
        this.up = up;
    }

    public Screen getDown() {
        return down;
    }

    public void setDown(Screen down) {
        this.down = down;
    }

    
}
