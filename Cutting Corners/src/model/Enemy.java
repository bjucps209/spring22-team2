package model;

import javafx.scene.image.Image;

public class Enemy extends Entity{
    EntityObserver observer;
    int sides;
    int size;
    Coordinates coords;
    Enemy type;

    public Enemy(int sides, int size, int xCoord, int yCoord, Image image){
        super(xCoord, yCoord, image);
        this.size = size;
        this.sides = sides;
        //this.coords = new Corrdinates(WIDTH, HEIGHT);
    }

    public void generateEnemy(int xCoord, int yCoord){
        type = new Triangle(size, xCoord, yCoord);
        // switch (sides) {
        //     case 3:{type = new Triangle(size);}
        //     default: break;
        // }
    }
}
