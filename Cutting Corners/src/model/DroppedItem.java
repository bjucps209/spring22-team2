package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class DroppedItem extends Entity {
    UsableItem unDroppedItem;
    playerInformer informant;
    Screen homeScreen;
    String text;
    boolean used = false;

    public DroppedItem(int xCoord, int yCoord, UsableItem unDroppedItem, String image, int size, Screen homeScreen) {
        super(xCoord, yCoord, image, size);
        this.unDroppedItem = unDroppedItem;
        this.homeScreen = homeScreen;
        text = "Press Space to Pick Up " + unDroppedItem.getName();
    }

    // called when the player presses SPACE, removes itself from the screen and
    // applies its @unDroppedItem's effects to the player
    public void pickUp(Player pickerUpper) {
        if (used) {
            return;
        }
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

    public void setUnDroppedCountdown(effectCountdown countdown) {
        unDroppedItem.setCountdown(countdown);
    }

    public void setHomeScreen(Screen homeScreen) {
        this.homeScreen = homeScreen;
    }

    // checks how close the player is, notifying them using @informant if they're
    // within 250 pixels and calling addItem()
    @Override
    public void performMovement() {
        PlayerRelation relation = playerOnScreen();
        if (relation != null) {
            addItem(relation);
            double distance = relation.getDistance();
            if (distance <= 250 && informant != null) {
                informant.Notify(text, this);
            } else if (distance > 250 && informant != null) {
                informant.Notify(null, this);
            }
        }
    }

    // adds itself to @player's itemsNearby using player's addItem() method
    public void addItem(PlayerRelation relation) {
        Player player = relation.getPlayer();
        if (!player.getScreenItems().contains(this))
            player.addItem(this);
    }

    // removes itself to @player's itemsNearby using player's removeItem() method
    public void removeItem() {
        Player player = playerOnScreen().getPlayer();
        player.removeItem(this);
    }

    // checks if and where the player is in relation to the item, returning
    // @relation is player is on screen, null otherwise
    public PlayerRelation playerOnScreen() {
        for (Entity entity : homeScreen.getEntities()) {
            if (entity instanceof Player) {
                int xDifference = super.getX() - entity.getX();
                int yDifference = super.getY() - entity.getY();
                double square = Math.pow(xDifference, 2) + Math.pow(yDifference, 2);
                double distance = Math.sqrt(square);
                PlayerRelation relation = new PlayerRelation(xDifference, yDifference, distance, (Player) entity);
                return relation;
            }
        }
        return null;
    }

    @Override
    public void serialize(DataOutputStream file) throws IOException {
    }
}