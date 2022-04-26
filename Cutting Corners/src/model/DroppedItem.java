package model;

import javax.net.ssl.HostnameVerifier;

public class DroppedItem extends Entity{
    Item unDroppedItem;
    playerInformer informant;
    Screen homeScreen;
    String text = "Press Space to Pick Up";

    public DroppedItem(int xCoord, int yCoord, Item unDroppedItem, String image, int size, Screen homeScreen){
        super(xCoord, yCoord, image, size);
        this.unDroppedItem = unDroppedItem;
        this.homeScreen = homeScreen;
    }

    public void pickUp(Player pickerUpper){
        // pickerUpper.addItem(unDroppedItem);
        World.instance().displayCurrentEntities().remove(this);
        unDroppedItem.performAction(pickerUpper);
    }

    public playerInformer getInformant() {
        return informant;
    }

    public void setInformant(playerInformer informant) {
        this.informant = informant;
    }

    @Override
    public void performMovement() {
        PlayerRelation relation = playerOnScreen();
        if (relation != null){
            double distance = relation.getDistance();
            if (distance <= 250){informant.Notify(text, this);}
        }
    }

    public PlayerRelation playerOnScreen(){
        for (Entity entity: homeScreen.getEntities()){
            if (entity instanceof Player){
                int xDifference = super.getX() - entity.getX();
                int yDifference = super.getY() - entity.getY();
                double square = Math.pow(xDifference, 2) + Math.pow(yDifference, 2);
                double distance = Math.sqrt(square);
                return new PlayerRelation(xDifference, yDifference, distance, (Player) entity);
            }
        }
        return null;
    }
}
