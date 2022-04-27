package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Enemy extends Entity{
    private Screen homeScreen;
    private Cell cellWithin;
    private int vision;
    private int sides;
    private Stats stats;
    private double totalHealth;
    private Enemy type;
    private Direction direction = Direction.left;
    private Equipment weapon = new MeleeWeapon("Basic Sword", 1, 1, 0, 0, 150, "media/Player/swordwalk.gif");
    private EnemyState state = EnemyState.patrolling;
    private int size;
    private int stunCount;
    private int knockback;
    private int attackCount=50;
    private String walking;
    private String attacking;
    private String currentImage = "Standing";
    private int experience;
    private int score;
    private Direction facing = Direction.left;
    private damageIndicator indicator;

    public Enemy(int sides, int size, int col, int row, String image, Screen homeScreen, int vision, Equipment weapon, Stats stats,String walking,String attacking,int totalHealth, int experience, int score)
    {
        super(col*100, row*100, image, size);
        this.score = score;
        this.homeScreen = homeScreen;
        this.vision = vision;
        this.sides = sides;
        this.weapon = weapon;
        this.stats = new Stats(stats.getStrength()*World.instance().getDifficulty(), stats.getSpeed()*World.instance().getDifficulty(), stats.getHealth()*World.instance().getDifficulty());
        this.size = size;
        this.totalHealth = totalHealth;
        this.walking=walking;
        this.attacking=attacking;
        cellWithin = cellWithin(col, row);
        this.experience = experience;
        //this.coords = new Corrdinates(WIDTH, HEIGHT);
    }
    public Stats getStats()
    {
        return stats;
    }
    public void generateEnemy(int xCoord, int yCoord){
        //type = new Triangle(super.getSize(), xCoord, yCoord, homeScreen);
        // switch (sides) {
        //     case 3:{type = new Triangle(size);}
        //     default: break;
        // }
    }

    public PlayerRelation PlayerInVision(){
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

    public Cell cellWithin(int row, int col){
        try{Cell cell = homeScreen.getGrid()[row][col];
            if (cell == null){cell = Cell.empty;}
        return cell;
        }catch(IndexOutOfBoundsException i){return Cell.empty;}
    }

    @Override
    public void performMovement(){
        PlayerRelation relation = PlayerInVision();
        if(relation!=null)
        {
            if(relation.getDistance()<=((MeleeWeapon) weapon).getRange()&&state!=EnemyState.stunned)
            {
                state=EnemyState.attacking;
                if(currentImage!="Attacking")
                {
                    super.getObserver().changeImage(attacking,Direction.left);
                    currentImage="Attacking";
                }
            }
        }
        switch (state){
            case stunned:
                stunCount--;
                if (knockback > 0){
                    simpleMovement(10);
                    knockback--;
                }
                if(stunCount<=0)
                {
                    attackCount=50;
                    state=EnemyState.patrolling;
                    if(currentImage!="Standing")
                    {
                        super.getObserver().changeImage(super.getImage(),Direction.left);
                        currentImage="Standing";
                    }
                }
                break;
            case patrolling: {
                if(relation!=null)
                {
                    if (relation.distance < vision&&state!=EnemyState.stunned) {
                        state = EnemyState.seeking; 
                        if(currentImage!="Walking")
                        {
                            super.getObserver().changeImage(walking,Direction.left);
                            currentImage="Walking";
                        }
                    }
                }
                break;
            }
            case seeking:{
                if(relation!=null)
                {
                    complexMovement(relation);
                }
            }
            case attacking: {
                if(attackCount==1)
                {
                    performAttack();
                }
                attackCount--;
                if (attackCount <= 0){
                    state = EnemyState.seeking;
                    attackCount=50;
                    if(currentImage!="Walking")
                    {
                        super.getObserver().changeImage(walking,Direction.left);
                        currentImage="Walking";
                    }
                }
            }
            default: break;
        }
    }

    @Override
    public void performAttack() {
        PlayerRelation p = PlayerInVision();
        if(p!=null)
        {
            MeleeWeapon weapon = (MeleeWeapon) this.weapon;
                weapon.setDamage(getStats().getStrength());
                weapon.setSpeed((int) getStats().getSpeed() / 2);
                if(p.getDistance()<=weapon.getRange())
                    {
                        if(p.getDirection()==Direction.up)
                        {
                            p.getPlayer().takeDamage(weapon.getDamage(), Direction.down);
                            System.out.println("Enemy Hit!");
                        }
                        else if(p.getDirection()==Direction.down)
                        {
                            p.getPlayer().takeDamage(weapon.getDamage(), Direction.up);
                            System.out.println("Enemy Hit!");
                        }
                        if(p.getDirection()==Direction.left)
                        {
                            p.getPlayer().takeDamage(weapon.getDamage(), Direction.right);
                            super.getObserver().changeImage(attacking, Direction.left);
                            System.out.println("Enemy Hit!");
                        }
                        else
                        {
                            p.getPlayer().takeDamage(weapon.getDamage(), Direction.left);
                            super.getObserver().changeImage(attacking, Direction.right);
                            System.out.println("Enemy Hit!");
                        }
                    }
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

        if (obstacleInPath(newX, newY)){
            switch (direction){
                case up: direction = Direction.left;
                case down: direction = Direction.right;
                case left: direction = Direction.down;
                case right: direction = Direction.up;
            }
            simpleMovement(stats.getSpeed());
            return;
        }
        
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
                if(facing==Direction.right)
                {
                    super.getObserver().changeImage(walking, Direction.right);
                    facing=Direction.left;
                }
                break;
            }
            case right: {
                super.getCoords().addXCoord(stats.getSpeed());
                if(facing==Direction.left)
                {
                    super.getObserver().changeImage(walking, Direction.left);
                    facing=Direction.right;
                }
                break;
            }
        }
    }

    public void simpleMovement(int speed){
        int xSpeed = 0;
        int ySpeed = 0;

        switch (direction){
            case up: ySpeed = -1 * speed;
            case down: ySpeed = speed;
            case left: xSpeed = -1 * speed;
            case right: xSpeed = speed;
        }

        super.getCoords().setxCoord(super.getX() + xSpeed);
        super.getCoords().setyCoord(super.getY() + ySpeed);
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

    @Override
    public void takeDamage(int damage, Direction direction){
        stats.subHealth(damage);
        state=EnemyState.stunned;
        if(currentImage!="Standing")
        {
            super.getObserver().changeImage(super.getImage(),Direction.left);
            currentImage="Standing";
        }
        knockback=10;
        this.direction = direction;
        if(stunCount<=0)
        {
            stunCount=50;
        }
        int time = damage * 100 + 500;
        indicator.displayDamage(this, damage, time);
        if (stats.getHealth() <= 0){performDie();}
    }

    @Override
    public void performDie(){
        World.instance().getPlayer().addExperience(experience);
        World.instance().getPlayer().addScore(score);
        super.performDie();
    }
    
    // Getters and Setters ---------------

    public Screen getHomeScreen() {
        return homeScreen;
    }

    public void setHomeScreen(Screen homeScreen) {
        this.homeScreen = homeScreen;
    }

    public damageIndicator getIndicator() {
        return indicator;
    }

    public void setIndicator(damageIndicator indicator) {
        this.indicator = indicator;
    }

    public Cell getCellWithin() {
        return cellWithin;
    }

    public void setCellWithin(Cell cellWithin) {
        this.cellWithin = cellWithin;
    }
    public int getScore()
    {
        return score;
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

    public void setTotalHealth(int totalHealth){
        this.totalHealth = totalHealth;
    }

    public double getTotalHealth(){
        return totalHealth;
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
        // TODO Auto-generated method stub
    }
    public void setAttackCount(int i) {
        attackCount=i;
    }

    
}
