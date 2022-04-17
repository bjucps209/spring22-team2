package model;

import javafx.scene.image.Image;

public class DroppedItem extends Entity{
    Item unDroppedItem;

    public DroppedItem(int xCoord, int yCoord, Item unDroppedItem, Image image, int size){
        super(xCoord, yCoord, image, size);
        this.unDroppedItem = unDroppedItem;
    }
}
