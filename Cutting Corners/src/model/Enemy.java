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
    Stats stats;
    Enemy type;
    Direction direction = Direction.left;
    Equipment weapon;
    EnemyState state = EnemyState.patrolling;
    int size;

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



    @Override
    public void serialize(DataOutputStream file) throws IOException {
        this.getCoords().serialize(file);
        homeScreen.serialize(file);
        file.writeUTF(cellWithin.toString());
        file.writeInt(vision);
        file.writeInt(sides);
        file.writeInt(super.getSize());
        stats.serialize(file);
        // direction ??
        weapon.serialize(file);
        file.writeUTF(state.toString());
    
    }
 
    @Override
    public void deserialize(DataInputStream file) throws IOException {
        this.getCoords().deserialize(file);
        homeScreen.deserialize(file);
        // cellWithin = file.readUTF(); add case statements
        this.vision = file.readInt();
        this.sides = file.readInt();
        this.size = file.readInt();
        this.stats.deserialize(file);

        this.weapon.deserialize(file);
        // this.state = file.readUTF();
        
    }

    
}
