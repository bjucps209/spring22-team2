//--------------------------------
// Enitity.java
// The parent class for enemies and items
// Defines the entities you see on the screen
//----------------------------------
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.beans.property.IntegerProperty;

public abstract class Entity {
    private int size;
    private Coordinates coords;
    private String image;
    private imageObserver imgObserver;
    private imageFlipper imgFlipper;
    private EntityObserver observer;

    public Entity(int xCoord, int yCoord, String image, int size){
        coords = new Coordinates(xCoord, yCoord);
        this.image = image;
    }

    public int getSize(){
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }

    public void setEventObservers(imageObserver observer, imageFlipper flipper){
        imgObserver = observer;
        imgFlipper = flipper;
    }
    
    public void setObserver(EntityObserver o)
    {
        observer=o;
    }

    public EntityObserver getObserver(){
        return observer;
    }

    public imageObserver getEObserver()
    {
        return imgObserver;
    }

    public imageFlipper getFlipper(){
        return imgFlipper;
    }

    public int getX(){
        return coords.getxCoord();
    }

    public int getY(){
        return coords.getyCoord();
    }

    public IntegerProperty getXProperty(){
        return coords.getXProperty();
    }

    public IntegerProperty getYProperty(){
        return coords.getYProperty();
    }

    public String getImage(){
        return image;
    }

    public void performMovement(){}
    public Coordinates getCoords() {
        return coords;
    }

    public void setCoords(Coordinates coords) {
        this.coords = coords;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public void takeDamage(int damage, Direction direction){
    }

    public void performDie(){
        World.instance().displayCurrentEntities().remove(this);
        World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
    }

    public void performAttack(){}


    public abstract void serialize(DataOutputStream file) throws IOException;


    
    /**
     * Factory method
     * Reads the variables left in the file by serialize.
     * Creates an instance of this class using those variables.
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static Entity deserialize(DataInputStream file, Screen homeScreen) throws IOException {
        String type = file.readUTF();

        // Player-----
        if (type.equals("Player")) {
            Player player = Player.deserialize(file);
            return player;

        // Projectile-----
        } else if (type.equals("Projectile")) {
            Projectile p = Projectile.deserialize(file);
            return p;

        // Enemies-----
        } else if (type.equals("Triangle")) {
            Triangle t = Triangle.deserialize(file, homeScreen);
            return t;
        } else if (type.equals("Square")) {
            Square s = Square.deserialize(file, homeScreen);
            return s;
        } else if (type.equals("Hexagon")) {
            Hexagon h = Hexagon.deserialize(file, homeScreen);
            return h;
        } else if (type.equals("Octagon")) {
            Octagon o = Octagon.deserialize(file, homeScreen);
            return o;

        // Bosses-----
        } else if (type.equals("Pyramid")) {
            Pyramid p = Pyramid.deserialize(file, homeScreen);
            return p;
        } else if (type.equals("Cube")) {
            Cube c = Cube.deserialize(file, homeScreen);
            return c;
        } else if (type.equals("Dodecahedron")) {
            Dodecahedron d = Dodecahedron.deserialize(file, homeScreen);
            return d;
        } else {                         // Circle
            Circle o = Circle.deserialize(file, homeScreen);
            return o;
        }
    }
}
