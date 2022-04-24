//-----------------------------------------------------------
//File:   LvLObject.java.java
//Desc:   Parent class of all placeable objects 
//----------------------------------------------------------- 
package Model;


public class LvLObject {
    protected String name;
    protected String imgPath;
    protected ObjType objType; // New system to replace Entity.java, Obstacle.java, etc
    protected Vector topLeftCell;
    protected Vector dimensions;
    protected int id; //   Null should crash system ___Maybe

    public LvLObject(String name, String imgPath, int id, ObjType objType, Vector dimensions, Vector topleftcell) {
        this.name = name;
        this.imgPath = imgPath;
        this.objType = objType;
        topLeftCell = topleftcell;
        this.dimensions = dimensions;
        this.id  = id;
    }

    //May not use
    public Vector moveLocation(Vector newLoc) {
        topLeftCell = newLoc;
        return newLoc;
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

    public ObjType getObjType() {
        return objType;
    }

    public void setObjType(ObjType objType) {
        this.objType = objType;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    
}
