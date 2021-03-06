//--------------------------------
// Screen.java
// Screen class defines the segments that make up a level
// Each screen posseses entities
//----------------------------------
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
    Random rand = new Random();

    public Screen(int row, int col, int level, String filename) {
        location = new Location(row, col, level);
        grid = new Cell[7][13];
        this.filename = filename;
        randomize();
    }

    // Adds a list of enemies to the entities on the screen
    public void addEnemyGroup(Enemy[] group) {
        for (Enemy enemy : group) {
            entities.add(enemy);
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        if (entity instanceof Enemy) {
            Enemy enemy = (Enemy) entity;
            enemy.setHomeScreen(this);
        } else if (entity instanceof DroppedItem) {
            DroppedItem item = (DroppedItem) entity;
            item.setHomeScreen(this);
        }
    }

    // Randomly places obstacles in the screen
    public void randomize() {
        fillGrid();
        if (!filename.contains("bossroom")) {
            for (int i = 1; i < 6; i++) {
                for (int j = 1; j < 12; j++) {
                    int random = rand.nextInt(40);
                    if (random == 39) {
                        grid[i][j] = Cell.rock;
                    } else {
                        grid[i][j] = Cell.empty;
                    }
                }
            }
        }
    }

    // sets grid to empty
    public void fillGrid() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 13; j++) {
                grid[i][j] = Cell.empty;
            }
        }
    }

    // Returns a list of the obstacles on this screen
    public ArrayList<Obstacle> findObstacles() {
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 13; j++) {
                if (grid[i][j] != Cell.empty) {
                    Obstacle obstacle = new Obstacle(i, j, grid[i][j]);
                    obstacles.add(obstacle);
                }
            }
        }
        return obstacles;
    }

    // Takes the player out of the list of entities
    public Player removePlayer() {
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                entities.remove(entity);
                return (Player) entity;
            }
        }
        return null;
    }

    // Getters and Setters -------------------------

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Location getLocation() {
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

    public ArrayList<Entity> getEnemies() {
        ArrayList<Entity> output = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof Enemy) {
                output.add((Enemy) e);
            }
        }
        return output;
    }

    public void setGridSquare(int row, int col, String cellType) {
        switch (cellType) {
            case "empty": {
                this.grid[row][col] = Cell.empty;
            }
            case "rock": {
                this.grid[row][col] = Cell.rock;
            }
            case "tree": {
                this.grid[row][col] = Cell.tree;
            }
            case "plant": {
                this.grid[row][col] = Cell.plant;
            }
            case "enemy": {
                this.grid[row][col] = Cell.enemy;
            }
        }
    }

    /**
     * Saves the state of this class with the necessary variables to a binary file
     * 
     * @param file
     * @throws IOException
     */
    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(location.getRow());
        file.writeInt(location.getCol());
        file.writeInt(location.getLevel());
        file.writeUTF(filename); // Background image

        file.writeInt(entities.size());
        for (Entity e : entities) {
            e.serialize(file);
        }
        int cellsTaken = 0;
        for (int row = 0; row < 7; ++row) {
            for (int col = 0; col < 13; ++col) {
                if (grid[row][col] != Cell.empty) {
                    cellsTaken++;
                }
            }
        }

        file.writeInt(cellsTaken);

        for (int row = 0; row < 7; ++row) {
            for (int col = 0; col < 13; ++col) {
                if (grid[row][col] != Cell.empty) {
                    file.writeUTF(grid[row][col].toString());
                    file.writeInt(row);
                    file.writeInt(col);
                }
            }
        }

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
    public static Screen deserialize(DataInputStream file) throws IOException {
        int row = file.readInt();
        int col = file.readInt();
        int level = file.readInt();
        String filename = file.readUTF(); // Background image
        Screen screen = new Screen(row, col, level, filename);

        int numEntities = file.readInt();
        for (int i = 0; i < numEntities; ++i) {
            Entity e = Entity.deserialize(file, screen);
            screen.addEntity(e);
            World.instance().getCurrentLevel().placeEntity(row, col, e);

        }
        int cellsTaken = file.readInt();
        for (int i = 0; i < cellsTaken; ++i) {
            String cellType = file.readUTF();
            int cellRow = file.readInt();
            int cellCol = file.readInt();
            if (cellType != "empty") {
                screen.setGridSquare(cellRow, cellCol, cellType);
            }
        }

        return screen;
    }

}
