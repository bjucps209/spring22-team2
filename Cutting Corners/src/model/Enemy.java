package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class Enemy extends Entity{
    private Screen homeScreen;
    private Cell cellWithin;
    private int vision;
    private int sides;
    private Stats stats;
    private Enemy type;
    private Direction direction = Direction.left;
    private Equipment weapon;
    private EnemyState state = EnemyState.patrolling;
    private int size;

    public Enemy(int sides, int size, int xCoord, int yCoord, Image image, Screen homeScreen, int vision, Equipment weapon, Stats stats){
        super(xCoord, yCoord, image, size);
        this.homeScreen = homeScreen;
        this.vision = vision;
        this.sides = sides;
        this.weapon = weapon;
        this.stats = stats;
        this.size = size;
        cellWithin = cellWithin(xCoord, yCoord);
        //this.coords = new Corrdinates(WIDTH, HEIGHT);
    }
    public Stats getStats()
    {
        return stats;
    }
    public void generateEnemy(int xCoord, int yCoord){
        type = new Triangle(super.getSize(), xCoord, yCoord, homeScreen);
        // switch (sides) {
        //     case 3:{type = new Triangle(size);}
        //     default: break;
        // }
    }

    public PlayerRelation PlayerInVision(){
        for (Entity entity: homeScreen.entities){
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

    public Cell cellWithin(int xCoord, int yCoord){
        int row = (int) super.getX() / 100 - 1;
        int col = (int) super.getY() / 100 - 1;

        Cell cell = homeScreen.grid[row][col];
        return cell;
    }

    @Override
    public void performMovement(){
        PlayerRelation relation = PlayerInVision();
        switch (state){
            case patrolling: {
                if (relation.distance < vision) state = EnemyState.seeking; 
                break;
            }
            case seeking:{
                complexMovement(relation);
            }
            default: break;
        }
    }

    public void complexMovement(PlayerRelation relation){
        int xSpeed = 0;
        int ySpeed = 0;

        switch (direction){
            case up: ySpeed = -1 * stats.getSpeed();
            case down: ySpeed = stats.getSpeed();
            case left: xSpeed = -1 * stats.getSpeed();
            case right: xSpeed = stats.getSpeed();
        }

        int newX = super.getX() + xSpeed;
        int newY = super.getY() + ySpeed;

        if (super.getX() % 100 > newX % 100){changeDirection(relation);}
        if (super.getY() % 100 > newY % 100){changeDirection(relation);}


        switch (direction){
            case up: {
                super.getCoords().subYCoord(stats.getSpeed());
                break;
            }
            case down: {
                super.getCoords().addYCoord(stats.getSpeed());
                break;
            }
            case left: {
                super.getCoords().subXCoord(stats.getSpeed());
                break;
            }
            case right: {
                super.getCoords().addXCoord(stats.getSpeed());
                break;
            }
        }
    }
    
    public void changeDirection(PlayerRelation relation){
        if (Math.abs(relation.xDifference) < Math.abs(relation.yDifference)){
            if (relation.yDifference > 0){direction = Direction.up;}
            else{direction = Direction.down;}
        }
        else{
            if (relation.xDifference > 0){direction = Direction.left;}
            else{direction = Direction.right;}
        }
    }

    public boolean obstacleInPath(){return false;}

    @Override
    public void takeDamage(int damage){
        stats.subHealth(damage);
        if (stats.getHealth() <= 0){super.performDie();}
    }


    
    // Getters and Setters ---------------

    public Screen getHomeScreen() {
        return homeScreen;
    }

    public void setHomeScreen(Screen homeScreen) {
        this.homeScreen = homeScreen;
    }

    public Cell getCellWithin() {
        return cellWithin;
    }

    public void setCellWithin(Cell cellWithin) {
        this.cellWithin = cellWithin;
    }

    public int getVision() {
        return vision;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    public int getSides() {
        return sides;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Enemy getType() {
        return type;
    }

    public void setType(Enemy type) {
        this.type = type;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Equipment getWeapon() {
        return weapon;
    }

    public void setWeapon(Equipment weapon) {
        this.weapon = weapon;
    }

    public EnemyState getState() {
        return state;
    }

    public void setState(EnemyState state) {
        this.state = state;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    



    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Enemy");
        file.writeInt(this.getX());
        file.writeInt(this.getY());
        homeScreen.serialize(file);
        file.writeInt(vision);
        file.writeInt(sides);
        file.writeInt(super.getSize());
        stats.serialize(file);
        // direction ??
        weapon.serialize(file);    
    }

    public static Enemy deserialize(DataInputStream file) throws IOException {
        int sides = file.readInt();
        int size = file.readInt();
        int x = file.readInt();
        int y = file.readInt();
        Screen homeScreen = Screen.deserialize(file);
        int vision = file.readInt();
        Equipment weapon = Equipment.deserialize(file);
        Stats stats = Stats.deserialize(file);

        Image image = new Image("basecase.png");

        Enemy enemy = new Enemy(sides, size, x, y, image, homeScreen, vision, weapon, stats);
        return enemy;
    }

    
}
