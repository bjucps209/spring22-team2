//--------------------------------
// Player.java
// The player class defines the entity who the users controls.
// The player can move across screens and advance levels by killing enemies.
//----------------------------------

package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class Player extends Entity {

    private ArrayList<Item> inventory;
    private String weaponImage = "media/Player/swordwalk.gif";
    private EntityObserver weaponObserver;
    private Item equippedItem = new MeleeWeapon("Basic Sword", 1, 1, 0, 0, 200, weaponImage);
    private Equipment armor;
    private Stats stats = new Stats(2, 5, 15);
    private static String playerImage = "media/Player/Cirkyle v1.png";
    private ArrayList<KeyCode> keys = new ArrayList<KeyCode>();
    private Coordinates mouseCoordinates = new Coordinates(0, 0);
    private PlayerState state = PlayerState.standing;
    private int attackCount = 25;
    private ArrayList<Entity> enemies;
    private Direction facing = Direction.left;
    private PlayerState previousState = PlayerState.standing;
    private double totalHealth = 15;
    private int walkingStep = 0;
    private int experience;
    private int score;
    private int knockedCount;
    private KeyCode keyPressed;
    private damageIndicator indicator;
    private ArrayList<DroppedItem> itemsNearby = new ArrayList<DroppedItem>();
    private ArrayList<Effect> effects = new ArrayList<Effect>();
    private BooleanProperty dead = new SimpleBooleanProperty(false);

    public Player(int xCoord, int yCoord) {
        super(xCoord, yCoord, playerImage, 500);
        applyEquipmentBuffs();
    }

    // Applies the effets of the equipment for the player
    public void applyEquipmentBuffs() {
        if (equippedItem != null) {
            if (equippedItem instanceof MeleeWeapon) {
                MeleeWeapon weapon = (MeleeWeapon) equippedItem;
                weapon.applyBuffs(this);
            } else if (equippedItem instanceof RangedWeapon) {
                RangedWeapon weapon = (RangedWeapon) equippedItem;
                weapon.applyBuffs(this);
            }
        }
        if (armor != null)
            armor.applyBuffs(this);
    }

    // Removes effects of Equipment for the player
    public void unApplyEquipmentBuffs() {
        if (equippedItem != null) {
            if (equippedItem instanceof MeleeWeapon) {
                MeleeWeapon weapon = (MeleeWeapon) equippedItem;
                weapon.unApplyBuffs(this);
            } else if (equippedItem instanceof RangedWeapon) {
                RangedWeapon weapon = (RangedWeapon) equippedItem;
                weapon.unApplyBuffs(this);
            }
        }
        if (armor != null)
            armor.unApplyBuffs(this);
    }

    // Performs the movement of the player based on his state and updates the view
    // accordingly
    @Override
    public void performMovement() {
        // itemsNearby.clear();
        if (!World.instance().getIsPaused()) {
            // if (effects.size() > 0){
            // applyBuffs();
            // }
            if (effects.size() > 0) {
                applyBuffs();
            }
            inObstacle();
            switch (state) {
                case standing: {
                    if (previousState == PlayerState.walking) {
                        walkingStep = 0;
                        super.getObserver().changeImage(playerImage, facing);
                        weaponObserver.changeImage(weaponImage, facing);
                        previousState = PlayerState.standing;
                    }
                    super.getObserver().changeImage(playerImage, facing);
                    weaponObserver.changeImage(weaponImage, facing);
                    if (keys.size() > 0) {
                        state = PlayerState.walking;
                        super.getObserver().changeImage("media/Player/basewalk.gif", facing);
                        weaponObserver.changeImage("media/player/swordwalk.gif", facing);
                    }
                    break;
                }
                case attacking: {
                    if (attackCount == 25) {
                        performAttack();
                        state = PlayerState.resting;
                    } else {
                        weaponObserver.changeImage("media/player/swordwalk.gif", facing);
                        state = PlayerState.resting;
                        break;
                    }
                }
                case resting: {
                    attackCount--;
                    if (attackCount == 0) {
                        state = PlayerState.standing;
                        weaponObserver.changeImage("media/player/swordwalk.gif", Direction.left);
                        if (facing == Direction.right) {
                            weaponObserver.changeImage("media/player/swordwalk.gif", Direction.right);
                        }
                        attackCount = 25;
                    }
                    break;
                }
                case walking: {
                    walkingStep = (walkingStep + 1) % 50;
                    if (keys.size() == 0) {
                        state = PlayerState.standing;
                    }
                    if (keys.size() > 0 && state != PlayerState.attacking) {
                        state = PlayerState.walking;
                    }
                    try {
                        KeyPressed(0);
                    } catch (IndexOutOfBoundsException i) {
                    }
                    break;
                }
                case drinking: {
                    if (itemsNearby.size() > 0) {
                        // attackCount=0;
                        DroppedItem item = findClosestItem();
                        item.pickUp(this);
                        weaponObserver.changeImage("media/Player/useItem.gif", Direction.left);
                    }
                    if (attackCount <= 0) {
                        attackCount = 25;
                        state = PlayerState.standing;
                        weaponObserver.changeImage("media/player/swordwalk.gif", Direction.right);
                    }
                    attackCount--;
                }
            }
        }

    }

    // determines the item on the screen that the player is closest to
    public DroppedItem findClosestItem() {
        int distance = 9999;
        DroppedItem closest = null;
        for (DroppedItem item : itemsNearby) {
            PlayerRelation relation = item.playerOnScreen();
            if (relation == null) {
                continue;
            }
            if (relation.getDistance() < distance) {
                closest = item;
            }
        }

        return closest;
    }

    // Applies the effects of a buff to the player
    public void applyBuffs() {
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            if (!effect.getApplied()) {
                effect.giveEffect(this);
            }
            effect.decrementDuration();
            if (effect.getDuration() <= 0) {
                effect.removeEffect(this);
            }
        }
    }

    public int getKeysSize() {
        return keys.size();
    }

    // Determines which key was pressed on the keyboard and does the action of that
    // key
    public void KeyPressed(int index) {
        if (!World.instance().getIsPaused()) {
            Direction direction = CheckIfOutOfBounds();
            if (state != PlayerState.standing) {
                if (state != PlayerState.walking) {
                    return;
                }
            }
            switch (keys.get(index)) {
                default:
                    return;
                case W: {
                    if (keys.size() > index + 1) {
                        KeyPressed((index + 1));
                    }
                    if (keys.contains(KeyCode.S)) {
                        return;
                    }
                    if (direction != Direction.up) {
                        super.getCoords().subYCoord(stats.getSpeed());
                    }

                    int newY = super.getY() - stats.getSpeed();
                    if (newY < -25 || obstacleInPath(super.getX(), newY)) {
                        super.getCoords().addYCoord(stats.getSpeed());
                    }
                    // if (cellWithin(super.getX()/100,super.getY()/100)!=Cell.empty){
                    // super.getCoords().subYCoord(100);
                    // }
                    break;
                }
                case A: {
                    if (keys.size() > index + 1) {
                        KeyPressed((index + 1));
                    }
                    if (keys.contains(KeyCode.D)) {
                        return;
                    }
                    if (direction != Direction.left) {
                        super.getCoords().subXCoord(stats.getSpeed());
                        if (facing == Direction.right) {
                            facing = Direction.left;
                            super.getObserver().changeImage("media/Player/basewalk.gif", Direction.left);
                            weaponObserver.changeImage(weaponImage, Direction.left);
                        }
                    }

                    int newX = super.getX() - stats.getSpeed();
                    if (newX < -25 || obstacleInPath(newX, super.getY())) {
                        super.getCoords().addXCoord(stats.getSpeed());
                    }
                    // if (cellWithin(super.getX()/100,super.getY()/100)!=Cell.empty){
                    // super.getCoords().subXCoord(100);
                    // }
                    break;
                }
                case S: {
                    if (keys.size() > index + 1) {
                        KeyPressed((index + 1));
                    }
                    if (keys.contains(KeyCode.W)) {
                        return;
                    }
                    if (direction != Direction.down) {
                        super.getCoords().addYCoord(stats.getSpeed());
                    }

                    int newY = super.getY() + stats.getSpeed();
                    if (super.getY() > 725 || obstacleInPath(super.getX(), newY)) {
                        super.getCoords().subYCoord(stats.getSpeed());
                    }
                    // if (cellWithin(super.getX()/100,super.getY()/100)!=Cell.empty){
                    // super.getCoords().addYCoord(100);
                    // }

                    break;
                }
                case D: {
                    if (keys.size() > index + 1) {
                        KeyPressed((index + 1));
                    }
                    if (keys.contains(KeyCode.A)) {
                        return;
                    }
                    if (direction != Direction.right) {
                        super.getCoords().addXCoord(stats.getSpeed());
                        if (facing == Direction.left) {
                            facing = Direction.right;
                            super.getObserver().changeImage("media/Player/basewalk.gif", Direction.right);
                            weaponObserver.changeImage(weaponImage, Direction.right);
                        }
                    }

                    int newX = super.getX() + stats.getSpeed();
                    if (super.getX() > 1225 || obstacleInPath(newX, super.getY())) {
                        super.getCoords().subXCoord(stats.getSpeed());
                    }

                    // if (cellWithin(super.getX()/100,super.getY()/100)!=Cell.empty){
                    // super.getCoords().addXCoord(stats.getSpeed());
                    // }
                    break;
                }
                // case SPACE: {
                // if (itemsNearby.size() > 0){
                // DroppedItem item = itemsNearby.get(0);
                // item.pickUp(this);
                // // super.getObserver().changeImage(i, d);
                // break;
                // }
                // }

                case ESCAPE:
                    if (!World.instance().getIsPaused()) {
                        World.instance().setIsPaused(true);
                        World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
                    } else {
                        World.instance().setIsPaused(false);
                        World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
                    }
            }
        } else {
            if (keys.get(index) == KeyCode.ESCAPE) {
                World.instance().setIsPaused(false);
                World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
            }
        }
    }

    // Determines if the player is out of bounds and keeps the player in the screen
    public Direction CheckIfOutOfBounds() {
        Level currentLevel = World.instance().getCurrentLevel();
        if (super.getCoords().getxCoord() > 1100 && currentLevel.getCurrentScreen().getRight() != null) {
            currentLevel.goRight();
            super.getObserver().changeImage("media/Player/basewalk.gif", facing);
        } else if (super.getCoords().getxCoord() > 1100) {
            return Direction.right;
        }
        if (super.getCoords().getxCoord() <= 0 && currentLevel.getCurrentScreen().getLeft() != null) {
            currentLevel.goLeft();
            super.getObserver().changeImage("media/Player/basewalk.gif", facing);
        } else if (super.getCoords().getxCoord() <= 0) {
            return Direction.left;
        }
        if (super.getCoords().getyCoord() >= 700 && currentLevel.getCurrentScreen().getDown() != null) {
            currentLevel.goDown();
            super.getObserver().changeImage("media/Player/basewalk.gif", facing);
        } else if (super.getCoords().getyCoord() >= 700) {
            return Direction.down;
        }
        if (super.getCoords().getyCoord() <= 0 && currentLevel.getCurrentScreen().getUp() != null) {
            currentLevel.goUp();
            super.getObserver().changeImage("media/Player/basewalk.gif", facing);
        } else if (super.getCoords().getyCoord() <= 0) {
            return Direction.up;
        }
        return null;
    }

    // Performs the single swing attack of the player.
    // inflicts damage if applicable
    // removes entity if killed
    @Override
    public void performAttack() {

        if (equippedItem instanceof MeleeWeapon) {
            if (facing == Direction.right) {
                weaponObserver.changeImage("media/player/swordattack.gif", Direction.left);
            } else {
                weaponObserver.changeImage("media/player/swordattack.gif", Direction.right);
            }
            AudioClip SWORD_ATTACK = new AudioClip(
                    getClass().getResource("/media/Sounds/Soundeffects/SwordAttack.mp3").toString());
            AudioClip SWORD_HIT = new AudioClip(
                    getClass().getResource("/media/Sounds/Soundeffects/SwordHit.mp3").toString());
            SWORD_ATTACK.play();
            MeleeWeapon weapon = (MeleeWeapon) equippedItem;
            weapon.setDamage(stats.getStrength());
            weapon.setSpeed((int) stats.getSpeed() / 2);
            enemies = World.instance().displayCurrentEntities();
            for (int i = 0; i < enemies.size(); i++) {
                if (!(enemies.get(i) instanceof Boss)) {
                    if (attackCount == 25
                            && Math.sqrt(Math.pow(enemies.get(i).getCoords().getxCoord() - super.getX(), 2)
                                    + Math.pow(enemies.get(i).getCoords().getyCoord() - super.getY(), 2)) <= weapon
                                            .getRange()) {
                        if (getMouseDirection() == Direction.up
                                && enemies.get(i).getCoords().getyCoord() > super.getY()) {
                            enemies.get(i).takeDamage(weapon.getDamage(), Direction.up);
                            SWORD_HIT.play();
                            continue;
                        } else if (getMouseDirection() == Direction.down
                                && enemies.get(i).getCoords().getyCoord() < super.getY()) {
                            enemies.get(i).takeDamage(weapon.getDamage(), Direction.down);
                            SWORD_HIT.play();
                            continue;
                        }
                        if (getMouseDirection() == Direction.left
                                && enemies.get(i).getCoords().getxCoord() > super.getX()) {
                            enemies.get(i).takeDamage(weapon.getDamage(), Direction.left);
                            SWORD_HIT.play();
                            continue;
                        } else if (getMouseDirection() == Direction.right
                                && enemies.get(i).getCoords().getxCoord() < super.getX()) {
                            enemies.get(i).takeDamage(weapon.getDamage(), Direction.right);
                            SWORD_HIT.play();
                            continue;
                        }
                    }
                } else {
                    if (((Boss) enemies.get(i)).getState() == EnemyState.patrolling) {
                        if (super.getX() > 300 && super.getX() < 800) {
                            enemies.get(i).takeDamage(weapon.getDamage(), facing);
                            SWORD_HIT.play();
                        }
                    }
                }
            }
        }
    }

    // Determines the direction the user is moving the mouse
    public Direction getMouseDirection() {
        if (Math.abs(mouseCoordinates.getxCoord() - (super.getX() + 100)) > Math
                .abs(mouseCoordinates.getyCoord() - (super.getY() + 100))) {
            if (super.getX() - mouseCoordinates.getxCoord() > 0) {
                return Direction.right;
            } else {
                return Direction.left;
            }
        } else {
            if (super.getY() - mouseCoordinates.getyCoord() > 0) {
                return Direction.down;
            } else {
                return Direction.up;
            }
        }
    }

    public void inObstacle() {
        if (obstacleInPath(super.getX(), super.getY())) {
            super.getCoords().setxCoord(super.getX() + 100);
        }
    }

    // Subtracts health from the player based on the damage taken
    @Override
    public void takeDamage(int damage, Direction direction) {
        if (!World.instance().getCheatMode()) {
            stats.subHealth(damage);
            int time = damage * 100 + 500;
            indicator.displayDamage(this, damage, time);

            switch (direction) {
                case up:
                    super.getCoords().addYCoord(100);
                case down:
                    super.getCoords().subYCoord(100);
                case left:
                    super.getCoords().subXCoord(100);
                case right:
                    super.getCoords().addXCoord(100);
            }

            if (stats.getHealth() <= 0) {
                performDie();
            }
        }
        AudioClip PLAYER_HURT = new AudioClip(
                getClass().getResource("/media/Sounds/Soundeffects/playerhurt.mp3").toString());
        PLAYER_HURT.play();

    }

    // Performs the death of an entity. Removes this player.
    @Override
    public void performDie() {
        HighScoreManager scores = new HighScoreManager();
        World.instance().getMusic().stop();
        try {
            scores.load();
            if (score > scores.get(0).getScore()) {
                scores.addScore(new HighScore(score, World.instance().getPlayerName()));
                scores.save();
            }
        } catch (IOException e) {
            System.out.println("couldn't Load");
            System.out.println(e.getMessage());
        }
        World.instance().displayCurrentEntities().remove(this);
        dead.set(true);
        World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
    }

    public Cell cellWithin(int row, int col) {
        try {
            Cell cell = World.instance().getCurrentLevel().getCurrentScreen().getGrid()[row][col];
            if (cell == null) {
                cell = Cell.empty;
            }
            return cell;
        } catch (Exception i) {
            return Cell.empty;
        }
    }

    // check to see if an obstacle would block players movement
    public boolean obstacleInPath(int xCoord, int yCoord) {
        int row = yCoord / 100;
        int col = xCoord / 100;

        for (int i = -1; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
                if (cellWithin(row + i, col + j) != Cell.empty) {
                    switch (cellWithin(row + i, col + j)) {
                        case tree:
                            if (i != 0 || j != 0) {
                                return false;
                            }
                        case plant:
                            if (i != -1 || j != 0) {
                                return false;
                            }
                        case rock:
                            if (i == -1) {
                                return false;
                            }
                        default:
                            return true;
                    }
                }
            }
        }

        return false;
    }

    // Getters and Setters -----------------------

    public void setWeaponObserver(EntityObserver img) {
        weaponObserver = img;
    }

    public EntityObserver getWeaponObserver() {
        return weaponObserver;
    }

    public Direction getFacing() {
        return facing;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public BooleanProperty getDead() {
        return dead;
    }

    public String getWeaponImage() {
        return weaponImage;
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public void addEffects(Effect effect) {
        effects.add(effect);
    }

    public void removeEffects(Effect effect) {
        effects.remove(effect);
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

    public void setState(PlayerState state) {
        this.state = state;
    }

    public int getAttackCount() {
        return attackCount;
    }

    public void setAttackCount(int attackCount) {
        this.attackCount = attackCount;
    }

    public void setTotalHealth(double totalHealth) {
        this.totalHealth = totalHealth;
    }

    public double getTotalHealth() {
        return totalHealth;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void addExperience(int addition) {
        experience += addition;
    }

    public int getExperience() {
        return experience;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int addition) {
        score += addition;
    }

    public void subScore(int difference) {
        score -= difference;
    }

    public int getScore() {
        return score;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void dropItem(Item item) {
        inventory.remove(item);
    }

    public void addKey(KeyCode event) {
        keys.add(event);
    }

    public void removeKey(KeyCode event) {
        keys.remove(event);
    }

    public ArrayList<KeyCode> getKeys() {
        return keys;
    }

    public void addItem(DroppedItem item) {
        itemsNearby.add(item);
    }

    public void removeItem(DroppedItem item) {
        itemsNearby.remove(item);
    }

    public ArrayList<DroppedItem> getScreenItems() {
        return itemsNearby;
    }

    public void setIndicator(damageIndicator indicator) {
        this.indicator = indicator;
    }

    public damageIndicator getIndicator() {
        return indicator;
    }

    public void setEquippedItem(Item equippedItem) {
        unApplyEquipmentBuffs();
        this.equippedItem = equippedItem;
        applyEquipmentBuffs();
    }

    public void setArmor(Armor armor) {
        unApplyEquipmentBuffs();
        this.armor = armor;
        applyEquipmentBuffs();
    }

    public void setEnemies(ArrayList<Entity> enemies) {
        this.enemies = enemies;
    }

    public void setMouseCoordinates(Coordinates coords) {
        mouseCoordinates = coords;
    }

    /**
     * Saves the state of this class with the necessary variables to a binary file
     * 
     * @param file
     * @throws IOException
     */
    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Player");
        file.writeInt(this.getX());
        file.writeInt(this.getY());

        equippedItem.serialize(file);
        stats.serialize(file);
    }

    /**
     * Factory method
     * Reads the variables left in the file by serialize.
     * Creates an instance of this class using those variables.
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static Player deserialize(DataInputStream file) throws IOException {
        // create a Player and return it with variables from the file
        int x = file.readInt();
        int y = file.readInt();
        Player player = new Player(x, y);

        player.setEquippedItem(Item.deserialize(file));
        player.setStats(Stats.deserialize(file));

        return player;
    }
}
