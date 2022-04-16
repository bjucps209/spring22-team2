//-----------------------------------------------------------
//File:   LvLObject.java.java
//Desc:   Parent class of all placeable objects 
//----------------------------------------------------------- 
package Model;


//TODO: implement random location
public abstract class LvLObject {
    protected String name;
    protected Vector objectLoc;
    protected Vector dimensions;
    protected int id; //Null should crash system

    public LvLObject(String name, double height, double width, double x, double y) {
        this.name = name;
        objectLoc = new Vector(x, y);
        dimensions = new Vector(height, width);
    }

    //Differs between Object types
    public Vector moveLocation(Vector newLoc) {
        objectLoc = newLoc;
        return newLoc;
    }

    public Vector getLocation() {
        return objectLoc;
    }

    public void setID(int newID) {
        id = newID;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public abstract boolean isStackable();
}
