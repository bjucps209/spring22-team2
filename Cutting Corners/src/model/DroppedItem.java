//--------------------------------
// DroppedItem.java
// Dropped items can be found across the map
// This class defines items to be used or picked up
//----------------------------------
package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class DroppedItem extends Entity{
    UsableItem unDroppedItem;
    playerInformer informant;
    Screen homeScreen;
    String text;
    boolean used = false;

    public DroppedItem(int xCoord, int yCoord, UsableItem unDroppedItem, String image, int size, Screen homeScreen){
        super(xCoord, yCoord, image, size);
        this.unDroppedItem = unDroppedItem;
        this.homeScreen = homeScreen;
        text = "Press Space to Pick Up " + unDroppedItem.getName();
    }

    public void pickUp(Player pickerUpper){
        if (used){return;}
        used = true;
        // pickerUpper.addItem(unDroppedItem);
        World.instance().displayCurrentEntities().remove(this);
        unDroppedItem.performAction(pickerUpper);
        // removeItem();
        World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
    }

    public playerInformer getInformant() {
        return informant;
    }

    public void setInformant(playerInformer informant) {
        this.informant = informant;
    }

    public void setUnDroppedCountdown(effectCountdown countdown){
        unDroppedItem.setCountdown(countdown);
    }

    public void setHomeScreen(Screen homeScreen){
        this.homeScreen = homeScreen;
    }

    @Override
    public void performMovement() {
        PlayerRelation relation = playerOnScreen();
        if (relation != null){
            addItem(relation);
            double distance = relation.getDistance();
            if (distance <= 250 && informant != null){informant.Notify(text, this);}
            else if (distance > 250 && informant != null){informant.Notify(null, this);}
        }
    }

    public void addItem(PlayerRelation relation){
        Player player = relation.getPlayer();
        if (! player.getScreenItems().contains(this)) player.addItem(this);
    }

    public void removeItem(){
        Player player = playerOnScreen().getPlayer();
        player.removeItem(this);
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


    @Override
    public void serialize(DataOutputStream file) throws IOException {
    }
}