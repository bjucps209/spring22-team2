//-----------------------------------------------------------
//File:   LvLObject.java.java
//Desc:   Parent class of all placeable objects 
//----------------------------------------------------------- 
package Model;


public abstract class LvLObject {
    protected String name;
    protected Vector topLeftCell;
    protected Vector dimensions;
    protected int id; //   Null should crash system ___

    public LvLObject(String name, int id,Vector dimensions, Vector topleftcell) {
        this.name = name;
        topLeftCell = topleftcell;
        this.dimensions = dimensions;
        this.id  = id;
    }

    //Differs between Object types
    public Vector moveLocation(Vector newLoc) {
        topLeftCell = newLoc;
        return newLoc;
    }

    public Vector getLTopLeftCell() {
        return topLeftCell;
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

    public Vector getTopLeftCell() {
        return topLeftCell;
    }

    public void setTopLeftCell(Vector topLeftCell) {
        this.topLeftCell = topLeftCell;
    }

    public Vector getDimensions() {
        return dimensions;
    }

    public void setDimensions(Vector dimensions) {
        this.dimensions = dimensions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
