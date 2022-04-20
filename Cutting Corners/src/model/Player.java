package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.*;

import javax.print.attribute.standard.DialogOwner;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;

public class Player extends Entity {

    ArrayList<Item> inventory;
    Image weaponImage = new Image ("media/Player/swordwalk.gif");
    Item equippedItem = new MeleeWeapon("Basic Sword", 1, 1, 0, 0, 250, 120, weaponImage);
    Equipment armor;
    Stats stats = new Stats(2, 5, 4);
    static Image playerImage = new Image("media/Player/Cirkyle v1.png");
    ArrayList<KeyCode> keys = new ArrayList<KeyCode>();
    Coordinates mouseCoordinates = new Coordinates(0, 0);
    PlayerState state = PlayerState.standing;
    int attackCount = 50;
    //Making a update so I can push
    ArrayList<Enemy> enemies;

    public Player(int xCoord, int yCoord){
        super(xCoord, yCoord, playerImage, 500);
        applyEquipmentBuffs();
    }

    public void addKey(KeyCode event){
        keys.add(event);
    }

    public void removeKey(KeyCode event){
        keys.remove(event);
    }

    public ArrayList<KeyCode> getKeys(){
        return keys;
    }

    public void setEquippedItem(Item equippedItem){
        unApplyEquipmentBuffs();
        this.equippedItem = equippedItem;
        applyEquipmentBuffs();
    }

    public void setArmor(Armor armor){
        unApplyEquipmentBuffs();
        this.armor = armor;
        applyEquipmentBuffs();
    }
    public void setEnemies(ArrayList<Enemy> enemies)
    {
        this.enemies=enemies;
    }

    public void setMouseCoordinates(Coordinates coords){
        mouseCoordinates = coords;
    }

    public void applyEquipmentBuffs(){
        if (equippedItem != null){
        if (equippedItem instanceof MeleeWeapon){
            MeleeWeapon weapon = (MeleeWeapon) equippedItem;
            weapon.applyBuffs(this);
        }
        else if (equippedItem instanceof RangedWeapon){
            RangedWeapon weapon = (RangedWeapon) equippedItem;
            weapon.applyBuffs(this);
        }
        }
        if (armor != null) armor.applyBuffs(this);
    }

    public void unApplyEquipmentBuffs(){
        if (equippedItem != null){
        if (equippedItem instanceof MeleeWeapon){
            MeleeWeapon weapon = (MeleeWeapon) equippedItem;
            weapon.unApplyBuffs(this);
        }
        else if (equippedItem instanceof RangedWeapon){
            RangedWeapon weapon = (RangedWeapon) equippedItem;
            weapon.unApplyBuffs(this);
        }
        }
        if (armor != null) armor.unApplyBuffs(this);
    }

    @Override
    public void performMovement(){

        switch (state) {
            case standing: {

                if (keys.size() > 0){state = PlayerState.walking;}
                break;
            }
            case walking: {
                if (keys.size() == 0){state = PlayerState.standing;}
                try{KeyPressed(0);}
                catch(IndexOutOfBoundsException i){}
                break;
            }
            case attacking: {
                if (attackCount <50){
                    state = PlayerState.resting; 
                    break;
                }
                Thread attack = new Thread(() -> performAttack());
                attack.start();
                state = PlayerState.resting;
            }
            case resting:{
                attackCount--;
                if (attackCount == 0){
                    state = PlayerState.standing; 
                    attackCount = 50;
                }
                break;
            }
        }
    }

    public int getKeysSize(){
        return keys.size();
    }

    public void KeyPressed(int index){
        Direction direction = CheckIfOutOfBounds();
        switch (keys.get(index)){
            default:
                if(state!=PlayerState.standing)
                {
                    if(state!=PlayerState.walking)
                    {
                        return;
                    }
                }
            case W: {
                if (keys.size() > index + 1){KeyPressed((index + 1));}
            if (direction != Direction.up){super.getCoords().subYCoord(stats.getSpeed());}
            if (super.getCoords().getyCoord() < 0){super.getCoords().addYCoord(stats.getSpeed());}
                break;
            }
            case A: {
                if (keys.size() > index + 1){KeyPressed((index + 1)); }
            if (direction != Direction.left){super.getCoords().subXCoord(stats.getSpeed());}
            if (super.getCoords().getxCoord() < 0){super.getCoords().addXCoord(stats.getSpeed());}
                break;
            }
            case S: {
                if (keys.size() > index + 1){KeyPressed((index + 1));} 
            if (direction != Direction.down){super.getCoords().addYCoord(stats.getSpeed());}
            if (super.getCoords().getyCoord() > 700){super.getCoords().subYCoord(stats.getSpeed());}
                break;
            }
            case D: {
                if (keys.size() > index + 1){KeyPressed((index + 1));}
            if (direction != Direction.right){super.getCoords().addXCoord(stats.getSpeed());}
            if (super.getCoords().getxCoord() > 1200){super.getCoords().subXCoord(stats.getSpeed());}
                break;
            }
        }
    }

    public Direction CheckIfOutOfBounds(){
        Level currentLevel = World.instance().getCurrentLevel();
        if (super.getCoords().getxCoord() > 1000 && currentLevel.getCurrentScreen().getRight() != null){
            currentLevel.goRight(); 
        }
        else if (super.getCoords().getxCoord() > 1000){
            return Direction.right;
        }
        if (super.getCoords().getxCoord() <= 0 && currentLevel.getCurrentScreen().getLeft() != null){
            currentLevel.goLeft(); 
        }
        else if (super.getCoords().getxCoord() <= 0){
            return Direction.left;
        }
        if (super.getCoords().getyCoord() >= 700 && currentLevel.getCurrentScreen().getDown() != null){
            currentLevel.goDown(); 
        }
        else if (super.getCoords().getyCoord() >= 700){
            return Direction.down;
        }
        if (super.getCoords().getyCoord() <= 0 && currentLevel.getCurrentScreen().getUp() != null){
            currentLevel.goUp(); 
        }
        else if (super.getCoords().getyCoord() <= 0){
            return Direction.up;
        }
        return null;
    }

    @Override
    public void performAttack(){
        if (equippedItem instanceof MeleeWeapon){
            // AudioClip SWORD_ATTACK = new AudioClip("media/Sounds/Sound effects/mixkit-dagger-woosh-1487-(Sword Attack).wav");
            // SWORD_ATTACK.play();
            MeleeWeapon weapon = (MeleeWeapon) equippedItem;
            weapon.setDamage(stats.getStrength());
            weapon.setSpeed((int) stats.getSpeed() / 2);
            for(int i=0;i<enemies.size();i++)
            {
                if(attackCount==50)
                {
                    if(getMousDirection()==Direction.up&&enemies.get(i).getCoords().getyCoord()>super.getY())
                    {
                        enemies.get(i).takeDamage(weapon.getDamage());
                        System.out.println("Hit!");
                        continue;
                    }
                    else if(getMousDirection()==Direction.down&&enemies.get(i).getCoords().getyCoord()<super.getY())
                    {
                        enemies.get(i).takeDamage(weapon.getDamage());
                        System.out.println("Hit!");
                        continue;
                    }
                    if(getMousDirection()==Direction.left&&enemies.get(i).getCoords().getxCoord()>super.getX())
                    {
                        enemies.get(i).takeDamage(weapon.getDamage());
                        System.out.println("Hit!");
                        continue;
                    }
                    else if(getMousDirection()==Direction.right&&enemies.get(i).getCoords().getxCoord()<super.getX())
                    {
                        enemies.get(i).takeDamage(weapon.getDamage());
                        System.out.println("Hit!");
                        continue;
                    }
                }
            }
            

            System.out.println(mouseCoordinates.getxCoord() - super.getX());
            System.out.println(mouseCoordinates.getyCoord() - super.getY());
            
            double slope = -1 * (mouseCoordinates.getyCoord() - super.getY() - 100) /
                           (mouseCoordinates.getxCoord() - super.getX() - 100);


            double direction = Math.atan(slope);

            if (mouseCoordinates.getyCoord() - super.getY() > 100 && 
                mouseCoordinates.getxCoord() - super.getX() > 100){
                    direction += 2 * Math.PI;
                }

            else if (mouseCoordinates.getyCoord() - super.getY() > 100 && 
                mouseCoordinates.getxCoord() - super.getX() < 100){
                    direction += Math.PI;
            }

            else if (mouseCoordinates.getyCoord() - super.getY() < 100 && 
                mouseCoordinates.getxCoord() - super.getX() < 100){
                    direction += Math.PI;
            }

            weapon.setDirection(direction);
        }
        
        equippedItem.performAction(this);
    }
    public Direction getMousDirection()
    {
        if(Math.sqrt(Math.pow(mouseCoordinates.getxCoord(), 2)+Math.pow(super.getX(), 2))>Math.sqrt(Math.pow(mouseCoordinates.getxCoord(), 2)+Math.pow(super.getX(), 2)))
        {
            if(super.getX()-mouseCoordinates.getxCoord()>0)
            {
                return Direction.right;
            }
            else
            {
                return Direction.left;
            }
        }
        else
        {
            if(super.getY()-mouseCoordinates.getyCoord()>0)
            {
                return Direction.down;
            }
            else
            {
                return Direction.up;
            }
        }
    }


    // Getters and Setters -----------------------
  
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public Image getWeaponImage() {
        return weaponImage;
    }

    public void setWeaponImage(Image weaponImage) {
        this.weaponImage = weaponImage;
    }

    public Item getEquippedItem() {
        return equippedItem;
    }

    public Equipment getArmor() {
        return armor;
    }

    public void setArmor(Equipment armor) {
        this.armor = armor;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public static Image getPlayerImage() {
        return playerImage;
    }

    public static void setPlayerImage(Image playerImage) {
        Player.playerImage = playerImage;
    }

    public void setKeys(ArrayList<KeyCode> keys) {
        this.keys = keys;
    }

    public Coordinates getMouseCoordinates() {
        return mouseCoordinates;
    }

    public PlayerState getState() {
        return state;
    }
    
    public void setState(PlayerState state){
        this.state = state;
    }

    public int getAttackCount() {
        return attackCount;
    }

    public void setAttackCount(int attackCount) {
        this.attackCount = attackCount;
    }    




    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Player");
        file.writeInt(this.getX());
        file.writeInt(this.getY());
        file.writeInt(inventory.size()); // how many items are in the inventory
        for (Item i : inventory) {
            i.serialize(file);
        }
        // equippedItem.serialize(file);
        armor.serialize(file);
        stats.serialize(file);
    }

    public static Player deserialize(DataInputStream file) throws IOException {
        // create a Player and return it with variables from the file
        int x = file.readInt();
        int y = file.readInt();
        Player player = new Player(x, y);

        int numItems = file.readInt();
        for (int i = 0; i < numItems; ++i) {
            Item item = Item.deserialize(file);
            player.getInventory().add(item);
        }
        // p.setEquippedItem(Item.deserialize(file);
        player.setArmor(Armor.deserialize(file));
        player.setStats(Stats.deserialize(file));

        return player;
    }
}
