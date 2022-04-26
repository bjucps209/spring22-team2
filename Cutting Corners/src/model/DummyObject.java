package model;

public class DummyObject {
    String name;
    String imgPath;
    String type;
    int tlCellX; int tlCellY; //Top left cell coords
    int dimX; int dimY; //Dimensions

    public DummyObject(String name, String imgPath, String type, int tlcX, int tlcY, int dimX, int dimY) {
        this.name = name; this.imgPath = imgPath;
        this.type = type;
        this.tlCellX = tlcX; this.tlCellY = tlcY;
        this.dimX = dimX; this.dimY = dimY;
    }

}
