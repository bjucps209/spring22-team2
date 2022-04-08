package model;

import javafx.scene.image.Image;

public class DroppedItem extends Entity{
    EntityObserver observer;
    Item unDroppedItem;

    public DroppedItem(int xCoord, int yCoord, Item unDroppedItem, Image image){
        super(xCoord, yCoord, image);
        this.unDroppedItem = unDroppedItem;
    }
}
