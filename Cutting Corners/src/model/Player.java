package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;

public class Player extends Entity {

    ArrayList<Item> inventory;
    String weaponImage = "media/Player/swordwalk.gif";
    private EntityObserver weaponObserver;
    Item equippedItem = new MeleeWeapon("Basic Sword", 1, 1, 0, 0, 150, weaponImage);
    Equipment armor;
    Stats stats = new Stats(2, 5, 15);
    static String playerImage = "media/Player/Cirkyle v1.png";
    ArrayList<KeyCode> keys = new ArrayList<KeyCode>();
    Coordinates mouseCoordinates = new Coordinates(0, 0);
    PlayerState state = PlayerState.standing;
    int attackCount = 25;
    ArrayList<Entity> enemies;
    Direction facing = Direction.left;
    PlayerState previousState = PlayerState.standing;
    double totalHealth = 15;
    int walkingStep = 0;
    int experience;
    int score;
    int knockedCount;
    KeyCode keyPressed;
    damageIndicator indicator;
    ArrayList<DroppedItem> itemsNearby = new ArrayList<DroppedItem>();

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

    public void addItem(DroppedItem item){
        itemsNearby.add(item);
    }

    public void setIndicator(damageIndicator indicator){
        this.indicator = indicator;
    }

    public damageIndicator getIndicator(){
        return indicator;
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

    public void setEnemies(ArrayList<Entity> enemies)
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
        itemsNearby.clear();

        switch (state) {
            case standing: {
                if (previousState == PlayerState.walking){
                    walkingStep = 0;
                    super.getObserver().changeImage(playerImage,facing);
                        weaponObserver.changeImage(weaponImage, facing);
                    previousState = PlayerState.standing;
                }
                super.getObserver().changeImage(playerImage,facing);
                        weaponObserver.changeImage(weaponImage, facing);
                if (keys.size() > 0){
                    state = PlayerState.walking;
                    super.getObserver().changeImage("media/Player/basewalk.gif",facing);
                    weaponObserver.changeImage("media/player/swordwalk.gif", facing);
                }
                break;
            }
            case attacking: {
                if (attackCount==25)
                {
                    performAttack();
                    state =PlayerState.resting;
                }
                else
                {
                    weaponObserver.changeImage("media/player/swordwalk.gif", facing);
                    state = PlayerState.resting; 
                    break;
                }
            }
            case resting:{
                attackCount--;
                if (attackCount == 0){
                    state = PlayerState.standing; 
                    weaponObserver.changeImage("media/player/swordwalk.gif", Direction.left);
                    if(facing==Direction.right)
                    {
                        weaponObserver.changeImage("media/player/swordwalk.gif", Direction.right);
                    }
                    attackCount = 25;
                }
                break;
            }
            case walking: {
                walkingStep = (walkingStep + 1) % 50;
                if (keys.size() == 0){state = PlayerState.standing;}
                if (keys.size() > 0&&state!=PlayerState.attacking){state = PlayerState.walking;}
                try{KeyPressed(0);}
                catch(IndexOutOfBoundsException i){}
                break;
            }
        }
    }

    public int getKeysSize(){
        return keys.size();
    }

    public void KeyPressed(int index){
        Direction direction = CheckIfOutOfBounds();
        if(state!=PlayerState.standing)
            {
                if(state!=PlayerState.walking)
                {
                    return;
                }
            }
        switch (keys.get(index)){
            default:
                return;
            case W: {
                if (keys.size() > index + 1){KeyPressed((index + 1));}
            if (keys.contains(KeyCode.S)){return;}
            if (direction != Direction.up){super.getCoords().subYCoord(stats.getSpeed());}

            
            int newY = super.getY() - stats.getSpeed();
            if (newY < -stats.getSpeed() || obstacleInPath(super.getX(), newY)){
                super.getCoords().addYCoord(stats.getSpeed());
            }
            // if (cellWithin(super.getX()/100,super.getY()/100)!=Cell.empty){
            //     super.getCoords().subYCoord(100);
            // }
                break;
            }
            case A: {
                if (keys.size() > index + 1){KeyPressed((index + 1)); }
            if (keys.contains(KeyCode.D)){return;}
            if (direction != Direction.left){
                super.getCoords().subXCoord(stats.getSpeed());
                if (facing == Direction.right){
                    facing = Direction.left;
                    super.getObserver().changeImage("media/Player/basewalk.gif", Direction.left);
                    weaponObserver.changeImage(weaponImage, Direction.left);
                }
            }
            
            int newX = super.getX() - stats.getSpeed();
            if (newX < -stats.getSpeed() || obstacleInPath(newX, super.getY())){
                super.getCoords().addXCoord(stats.getSpeed());
            }
            // if (cellWithin(super.getX()/100,super.getY()/100)!=Cell.empty){
            //     super.getCoords().subXCoord(100);
            // }
                break;
            }
            case S: {
                if (keys.size() > index + 1){KeyPressed((index + 1));} 
            if (keys.contains(KeyCode.W)){return;}
            if (direction != Direction.down){super.getCoords().addYCoord(stats.getSpeed());}

            int newY = super.getY() + stats.getSpeed();
            if (super.getY() > 700 || obstacleInPath(super.getX(), newY)){
                super.getCoords().subYCoord(stats.getSpeed());
            }
            // if (cellWithin(super.getX()/100,super.getY()/100)!=Cell.empty){
            //     super.getCoords().addYCoord(100);
            // }
            
                break;
            }
            case D: {
                if (keys.size() > index + 1){KeyPressed((index + 1));}
            if (keys.contains(KeyCode.A)){return;}
            if (direction != Direction.right){
                super.getCoords().addXCoord(stats.getSpeed());
                if (facing == Direction.left){
                    facing = Direction.right;
                    super.getObserver().changeImage("media/Player/basewalk.gif", Direction.right);
                    weaponObserver.changeImage(weaponImage, Direction.right);
                }
            }
            
            int newX = super.getX() + stats.getSpeed();
            if (super.getX() > 1200 || obstacleInPath(newX, super.getY())){
                super.getCoords().subXCoord(stats.getSpeed());
            }

            // if (cellWithin(super.getX()/100,super.getY()/100)!=Cell.empty){
            //     super.getCoords().addXCoord(stats.getSpeed());
            // }
                break;
            }
            case SPACE: {
                if (itemsNearby.size() > 0){
                    DroppedItem item = itemsNearby.get(0);
                    item.pickUp(this);
                    // super.getObserver().changeImage(i, d);
                    break;
                }
            }
        }
    }

    public Direction CheckIfOutOfBounds(){
        Level currentLevel = World.instance().getCurrentLevel();
        if (super.getCoords().getxCoord() > 1100 && currentLevel.getCurrentScreen().getRight() != null){
            currentLevel.goRight(); 
        }
        else if (super.getCoords().getxCoord() > 1100){
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
            if(facing==Direction.right)
            {
                weaponObserver.changeImage("media/player/swordattack.gif",Direction.left);
            }
            else
            {
                weaponObserver.changeImage("media/player/swordattack.gif",Direction.right);
            }
            AudioClip SWORD_ATTACK = new AudioClip(getClass().getResource("/media/Sounds/Soundeffects/SwordAttack.mp3").toString());
            AudioClip SWORD_HIT = new AudioClip(getClass().getResource("/media/Sounds/Soundeffects/SwordHit.mp3").toString());
            SWORD_ATTACK.play();
            MeleeWeapon weapon = (MeleeWeapon) equippedItem;
            weapon.setDamage(stats.getStrength());
            weapon.setSpeed((int) stats.getSpeed() / 2);
            for(int i=0;i<enemies.size();i++)
            {
                if(!(enemies.get(i) instanceof Boss))
                {
                    if(attackCount==25&&Math.sqrt(Math.pow(enemies.get(i).getCoords().getxCoord()-super.getX(), 2)+Math.pow(enemies.get(i).getCoords().getyCoord()-super.getY(), 2))<=weapon.getRange())
                    {
                        if(getMouseDirection()==Direction.up&&enemies.get(i).getCoords().getyCoord()>super.getY())
                        {
                            enemies.get(i).takeDamage(weapon.getDamage(), Direction.up);
                            SWORD_HIT.play();
                            continue;
                        }
                        else if(getMouseDirection()==Direction.down&&enemies.get(i).getCoords().getyCoord()<super.getY())
                        {
                            enemies.get(i).takeDamage(weapon.getDamage(), Direction.down);
                            SWORD_HIT.play();
                            continue;
                        }
                        if(getMouseDirection()==Direction.left&&enemies.get(i).getCoords().getxCoord()>super.getX())
                        {
                            enemies.get(i).takeDamage(weapon.getDamage(), Direction.left);
                            SWORD_HIT.play();
                            continue;
                        }
                        else if(getMouseDirection()==Direction.right&&enemies.get(i).getCoords().getxCoord()<super.getX())
                        {
                            enemies.get(i).takeDamage(weapon.getDamage(), Direction.right);
                            SWORD_HIT.play();
                            continue;
                        }
                    }
                }
                else
                {
                    if(((Enemy)enemies.get(i)).getState()==EnemyState.patrolling)
                    {
                        if(super.getX()>500&&super.getX()<750)
                        {
                            enemies.get(i).takeDamage(weapon.getDamage(), facing);
                        }
                    }
                }
            }
        }
    }
    public Direction getMouseDirection()
    {
        if(Math.abs(mouseCoordinates.getxCoord()-(super.getX()+100))>Math.abs(mouseCoordinates.getyCoord()-(super.getY()+100)))
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

    @Override
    public void takeDamage(int damage, Direction direction)
    {
        stats.subHealth(damage);
        AudioClip PLAYER_HURT = new AudioClip(getClass().getResource("/media/Sounds/Soundeffects/playerhurt.mp3").toString());
        PLAYER_HURT.play();
        if (stats.getHealth() <= 0){performDie();}
        int time = damage * 100 + 500;
        indicator.displayDamage(this, damage, time);

        switch (direction){
            case up: super.getCoords().addYCoord(100);
            case down: super.getCoords().subYCoord(100);
            case left: super.getCoords().subXCoord(100);
            case right: super.getCoords().addXCoord(100);
        }

        if (stats.getHealth() <= 0){super.performDie();}
    }

    @Override
    public void performDie()
    {
        World.instance().displayCurrentEntities().remove(this);
        World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
        HighScoreManager scores = new HighScoreManager();
        try
        {
            scores.load();
            if(score>scores.get(0).getScore())
            {
                scores.addScore(new HighScore(score, "Player"));
                scores.save();
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    public Cell cellWithin(int row, int col){
        Cell cell = World.instance().getCurrentLevel().getCurrentScreen().getGrid()[row][col];
        if (cell == null){cell = Cell.empty;}
        return cell;
    }

    public boolean obstacleInPath(int xCoord, int yCoord){
        int row = yCoord / 100;
        int col = xCoord / 100;
        

        for (int i = -1; i < 1; i++){
            for (int j = 0; j < 2; j++){
                if (cellWithin(row + i, col + j) != Cell.empty){
                    switch(cellWithin(row + i, col + j)){
                        case tree: if (i != 0 || j != 0){return false;}
                        case plant: if (i != -1 || j != 0){return false;}
                        case rock: if (i == -1){return false;}
                        default: return true;
                    }
                }
            }
        }

        return false;
    }

    // Getters and Setters -----------------------
   public void setWeaponObserver(EntityObserver img)
   {
       weaponObserver=img;
   } 

   public EntityObserver getWeaponObserver()
   {
       return weaponObserver;
   }

   public Direction getFacing()
   {
       return facing;
   }
    
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public String getWeaponImage() {
        return weaponImage;
    }

    public void setWeaponImage(String weaponImage) {
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

    public static String getPlayerImage() {
        return playerImage;
    }

    public static void setPlayerImage(String playerImage) {
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
    
    public void setTotalHealth(double totalHealth){
        this.totalHealth = totalHealth;
    }

    public double getTotalHealth(){
        return totalHealth;
    }

    public void setExperience(int experience){
        this.experience = experience;
    }

    public void addExperience(int addition){
        experience += addition;
    }

    public int getExperience(){
        return experience;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void addScore(int addition){
        score += addition;
    }

    public void subScore(int difference){
        score -= difference;
    }

    public int getScore(){
        return score;
    }

    public void addItem(Item item){
        inventory.add(item);
    }

    public void dropItem(Item item){
        inventory.remove(item);
    }


    @Override
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
