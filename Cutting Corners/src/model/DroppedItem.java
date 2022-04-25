package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class DroppedItem extends Entity{
    Item unDroppedItem;

    public DroppedItem(int xCoord, int yCoord, Item unDroppedItem, String image, int size){
        super(xCoord, yCoord, image, size);
        this.unDroppedItem = unDroppedItem;
    }

    public void pickUp(Player pickerUpper){
        pickerUpper.addItem(unDroppedItem);
    }


    @Override
    public void serialize(DataOutputStream file) throws IOException {
        unDroppedItem.serialize(file);
    }
}