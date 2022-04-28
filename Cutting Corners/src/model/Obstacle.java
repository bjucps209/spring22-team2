//--------------------------------
// Obstacle.java
// Obstacles are positioned throughout each screen at a specif x and y coord
//----------------------------------
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class Obstacle {
    private int row;
    private int col;
    private Cell type;

    public Obstacle(int row, int col, Cell type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public int getX(){
        return col * 100;
    }

    public int getY(){
        return row * 100;
    }

    public Image getImage(){return null;}


    

    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(row);
        file.writeInt(col);
        file.writeUTF(type.toString());
    }

    public static Obstacle deserialize(DataInputStream file) throws IOException {
        int row = file.readInt();
        int col = file.readInt();
        String type = file.readUTF();
        Cell obstacleType = Cell.empty;
        if (type.equals("rock")) {
            obstacleType = Cell.rock;
        } else if (type.equals("tree")) {
            obstacleType = Cell.tree;
        } else {
            obstacleType = Cell.plant;
        }

        Obstacle o = new Obstacle(row, col, obstacleType);
        return o;
    }
}
