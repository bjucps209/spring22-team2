package model;

public class DroppedItem extends Entity{
    Item unDroppedItem;

    public DroppedItem(int xCoord, int yCoord, Item unDroppedItem, String image, int size){
        super(xCoord, yCoord, image, size);
        this.unDroppedItem = unDroppedItem;
    }

    public void pickUp(Player pickerUpper){
        pickerUpper.addItem(unDroppedItem);
    }
}
