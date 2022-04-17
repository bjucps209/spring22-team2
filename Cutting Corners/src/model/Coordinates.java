package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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



    
    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(xCoord.get());
        file.writeInt(yCoord.get());
    }

    public void deserialize(DataInputStream file) throws IOException {
        xCoord.set(file.readInt());
        yCoord.set(file.readInt());
    }
}
