package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class Obstacle {
    int row;
    int col;
    Cell type;

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
    
    }

    public void deserialize(DataInputStream file) throws IOException {
        
    }
}
