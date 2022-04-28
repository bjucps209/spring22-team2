//--------------------------------
// Coordinates.java
// Coordinates class provides easy access to
// x and y coordinates for cells and entities.
// Utilizes IntegerProperty for binding
//----------------------------------
package model;
import java.util.Random;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Coordinates {
    private IntegerProperty xCoord = new SimpleIntegerProperty();
    private IntegerProperty yCoord = new SimpleIntegerProperty();

    public Coordinates(int xCoord, int yCoord){
        this.xCoord.set(xCoord);
        this.yCoord.set(yCoord);
    }

    // randomly generates coordinates using @rand and returning @coords
    public static Coordinates randomCoords(int WIDTH, int HEIGHT){
        Random rand = new Random();
        Coordinates coords = new Coordinates(0,0);
        coords.setxCoord(rand.nextInt(WIDTH));
        coords.setyCoord(rand.nextInt(HEIGHT));
        return coords;
    }

    public int getxCoord() {
        return xCoord.get();
    }

    public void setxCoord(int xCoord) {
        this.xCoord.set(xCoord);
    }

    public int getyCoord() {
        return yCoord.get();
    }

    public void setyCoord(int yCoord) {
        this.yCoord.set(yCoord);
    }

    public void addXCoord(int addition){
        xCoord.set(xCoord.get() + addition);
    }
    
    public void subXCoord(int difference){
        xCoord.set(xCoord.get() - difference);
    }

    public void addYCoord(int addition){
        yCoord.set(yCoord.get() + addition);
    }

    public void subYCoord(int difference){
        yCoord.set(yCoord.get() - difference);
    }

    public IntegerProperty getXProperty(){
        return xCoord;
    }
    
    public IntegerProperty getYProperty(){
        return yCoord;
    }
}
