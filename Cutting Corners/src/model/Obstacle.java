package model;

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
}
