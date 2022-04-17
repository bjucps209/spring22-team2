package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class Enemy extends Entity{
    Screen homeScreen;
    Cell cellWithin;
    int vision;
    int sides;
    int size;
    Stats stats;
    Enemy type;
    Direction direction = Direction.left;
    Equipment weapon;
    EnemyState state = EnemyState.patrolling;

    public Enemy(int sides, int size, int xCoord, int yCoord, Image image, Screen homeScreen, int vision, Equipment weapon, Stats stats){
        super(xCoord, yCoord, image);
        this.homeScreen = homeScreen;
        this.vision = vision;
        this.size = size;
        this.sides = sides;
        this.weapon = weapon;
        this.stats = stats;
        cellWithin = cellWithin(xCoord, yCoord);
        //this.coords = new Corrdinates(WIDTH, HEIGHT);
    }

    public void generateEnemy(int xCoord, int yCoord){
        type = new Triangle(size, xCoord, yCoord, homeScreen);
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

        System.out.println(xSpeed);
        System.out.println(ySpeed);

        int newX = super.getX() + xSpeed;
        int newY = super.getY() + ySpeed;

        if (super.getX() % 100 > newX % 100){changeDirection(relation);}
        if (super.getY() % 100 > newY % 100){changeDirection(relation);}

        switch (direction){
            case up: super.getCoords().subYCoord(stats.getSpeed());
            case down: super.getCoords().addYCoord(stats.getSpeed());
            case left: super.getCoords().subXCoord(stats.getSpeed());
            case right: super.getCoords().addXCoord(stats.getSpeed());
        }
    }
    
    public void changeDirection(PlayerRelation relation){
        if (Math.abs(relation.xDifference) < Math.abs(relation.yDifference)){
            if (relation.yDifference > 0){direction = Direction.down;}
            else{direction = Direction.up;}
        }
        else{
            if (relation.xDifference > 0){direction = Direction.left;}
            else{direction = Direction.right;}
        }
    }

    public boolean obstacleInPath(){return false;}




    public void serialize(DataOutputStream file) throws IOException {
    
    }

    public void deserialize(DataInputStream file) throws IOException {
        
    }

    
}
