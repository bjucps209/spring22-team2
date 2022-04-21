package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class Screen {
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private Cell[][] grid;
    private Location location;
    private Screen left;
    private Screen right;
    private Screen up;
    private Screen down;
    private String filename;
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    Random rand = new Random();

    public Screen(int row, int col, int level,String filename){
        location = new Location(row, col, level);
        grid = new Cell[7][13];
        this.filename=filename;
        randomize();
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
    public ArrayList<Entity> getEnemies()
    {
        ArrayList<Entity> output = new ArrayList<>();
        for(Entity e:entities)
        {
            if(e instanceof Enemy)
            {
                output.add((Enemy)e);
            }
        }
        return output;
    }


    // Getters and Setters -------------------------
    
    public Location getLocation(){
        return location;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public void setLocation(Location location) {
        this.location = location;
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


    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(location.getRow());
        file.writeInt(location.getCol());
        file.writeInt(location.getLevel());
        file.writeUTF(filename);
        file.writeInt(entities.size());
        for (Entity e : entities) {
            e.serialize(file);
        }
        for (int row = 0; row < 7; ++row) {
            for (int col = 0; col < 13; ++col) {
                file.writeUTF(grid[row][col].toString());
            }
        }
    
    }

    public static Screen deserialize(DataInputStream file) throws IOException {
        int row = file.readInt();
        int col = file.readInt();
        int level = file.readInt();
        String filename = file.readUTF();
        Screen s = new Screen(row, col, level,filename);

        int numEntities = file.readInt();
        for (int i = 0; i < numEntities; ++i) {
            Entity e = Entity.deserialize(file);
            s.getEntities().add(e);
        }
        for (int r = 0; r < 7; ++r) {
            for (int c = 0; c < 13; ++c) {
                String cellType = file.readUTF();
                switch (cellType) {
                    case "empty": {
                        s.getGrid()[r][c] = Cell.empty;
                    }
                    case "rock": {
                        s.getGrid()[r][c] = Cell.rock;
                    }
                    case "tree": {
                        s.getGrid()[r][c] = Cell.tree;
                    }
                    case "plant": {
                        s.getGrid()[r][c] = Cell.plant;
                    }
                    case "enemy": {
                        s.getGrid()[row][col] = Cell.enemy;
                    }
                }
            }
        }

        return s; 
    }

    
}
